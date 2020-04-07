package life.qbic.repowiz.io

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.BaseJsonValidator
import com.networknt.schema.JsonSchema
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.ValidationMessage
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


class JsonParser {//implements TemplateParser{

    private Map jsonFile

    private static final Logger LOG = LogManager.getLogger(JsonParser.class)

    def parseAsFile(String jsonFile)throws IOException{
        //todo should not load from class when user forwards config file path
        String file = JsonParser.class.getClassLoader().getResource(jsonFile).getPath()

        if(file != null) return new JsonSlurper().parseText(new File (file).text)

        throw new IOException("The specified file was not found: "+jsonFile)
    }

    def parseAsStream(String jsonFile)throws IOException{
        //todo should not load from class when user forwards conifg file path
        InputStream stream = JsonParser.class.getClassLoader().getResourceAsStream(jsonFile)

        if(stream != null) return new JsonSlurper().parseText(stream.text)

        throw new IOException("The specified file was not found: "+jsonFile)
    }

    void getMapFromJsonFile(String jsonFilePath){
        def json = parseAsStream(jsonFilePath)

        assert json instanceof Map

         jsonFile = (Map) json
    }

    def validate(String schemaPath){
        JsonSchema schema = getJsonSchemaFromClasspath(schemaPath)
        JsonNode node = getJsonNodeFromMapContent(jsonFile)
        Set<ValidationMessage> errors = schema.validate(node)

        if(errors.size() != 0){
            throw new IllegalArgumentException("The repository description is not valid. Check the repository.schema for detaild specification! $errors")
        }
    }

    protected JsonNode getJsonNodeFromMapContent(Map content) throws Exception {

        ObjectMapper mapper = new ObjectMapper()
        JsonNode node = mapper.readTree(JsonOutput.toJson(content))

        return node
    }

    protected JsonSchema getJsonSchemaFromClasspath(String name) throws Exception {
        JsonSchemaFactory factory = JsonSchemaFactory.instance
        InputStream is = JsonParser.class.getClassLoader().getResourceAsStream(name)
        JsonSchema schema = factory.getSchema(is)
        return schema
    }
}
