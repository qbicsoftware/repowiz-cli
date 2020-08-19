package life.qbic.repowiz.io

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.JsonSchema
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.ValidationMessage
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * This class defines how file in JSON format are parsed
 *
 * It should be used when JSON files need to be used from within RepoWiz
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class JsonParser {

    private InputStream stream

    private static final Logger LOG = LogManager.getLogger(JsonParser.class)

    /**
     * Creates a parser for a stream
     * @param stream with the content of a JSON file
     */
    JsonParser(InputStream stream) {
        this.stream = stream
    }

    /**
     * Creates a map from the file content of a JSON file
     * @return a map with file content
     */
    Map parse() {
        def json = new JsonSlurper().parseText(stream.text)

        assert json instanceof Map

        return (Map) json
    }

    /**
     * Validates a schema and throws an error if the validation fails
     * @param schemaPath describes the path to the schema
     * @param data describes the data that needs to be validated
     */
    def static validate(String schemaPath, Map data) {
        JsonSchema schema = getJsonSchemaFromClasspath(schemaPath)
        JsonNode node = getJsonNodeFromMapContent(data)
        Set<ValidationMessage> errors = schema.validate(node)

        if (errors.size() != 0) {
            errors.each { error ->
                LOG.error error.message
            }
            throw new IllegalArgumentException("The repository description is not valid. Check the repository.schema for detailed specification!")
        }
    }

    /**
     * Creates a JSON node from the content of a map
     * @param content from a JSON file
     * @return a json node with the map content
     * @throws Exception
     */
    protected static JsonNode getJsonNodeFromMapContent(Map content) throws Exception {
        ObjectMapper mapper = new ObjectMapper()
        JsonNode node = mapper.readTree(JsonOutput.toJson(content))

        return node
    }

    /**
     * Creates a JSON schema from a string containing a file path
     * @param name of the schema path
     * @return a json schema
     * @throws Exception
     */
    protected static JsonSchema getJsonSchemaFromClasspath(String name) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.instance
        InputStream is = JsonParser.class.getClassLoader().getResourceAsStream(name)
        JsonSchema schema = factory.getSchema(is)

        return schema
    }
}
