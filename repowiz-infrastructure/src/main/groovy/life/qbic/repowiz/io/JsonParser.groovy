package life.qbic.repowiz.io

import groovy.json.JsonSlurper

import java.nio.file.Path

class JsonParser {//implements TemplateParser{

    /*String jsonFile

    JsonParser(String file){
        jsonFile = file
    }*/

    def parseAsFile(String jsonFile){
        String file = getClass().getResource(jsonFile).getPath()
        new JsonSlurper().parseText(new File (file).text)
    }

    def parseAsStream(String jsonFile){
        InputStream stream = JsonParser.class.getClassLoader().getResourceAsStream(jsonFile)
        new JsonSlurper().parseText(stream.text)
    }
}
