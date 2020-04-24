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


class JsonParser {

    private InputStream stream

    private static final Logger LOG = LogManager.getLogger(JsonParser.class)

    JsonParser(InputStream stream){
        this.stream = stream
    }

    Map parse(){
        def json = new JsonSlurper().parseText(stream.text)

        assert json instanceof Map

        return  (Map) json
    }


    def static validate(String schemaPath, Map data){
        JsonSchema schema = getJsonSchemaFromClasspath(schemaPath)
        JsonNode node = getJsonNodeFromMapContent(data)
        Set<ValidationMessage> errors = schema.validate(node)

        if(errors.size() != 0){
            throw new IllegalArgumentException("The repository description is not valid. Check the repository.schema for detaild specification! $errors")
        }
    }

    protected static JsonNode getJsonNodeFromMapContent(Map content) throws Exception {
        ObjectMapper mapper = new ObjectMapper()
        JsonNode node = mapper.readTree(JsonOutput.toJson(content))

        return node
    }

    protected static JsonSchema getJsonSchemaFromClasspath(String name) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.instance
        InputStream is = JsonParser.class.getClassLoader().getResourceAsStream(name)
        JsonSchema schema = factory.getSchema(is)

        return schema
    }
}
