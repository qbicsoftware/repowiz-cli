package life.qbic.repowiz.io

import groovy.json.JsonSlurper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.nio.file.Path

class JsonParser {//implements TemplateParser{

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

    Map getMapFromJsonFile(String jsonFilePath){
        def json = parseAsStream(jsonFilePath)

        assert json instanceof Map

        return (Map) json
    }
}
