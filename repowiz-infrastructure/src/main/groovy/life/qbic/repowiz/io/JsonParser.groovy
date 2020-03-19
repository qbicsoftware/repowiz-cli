package life.qbic.repowiz.io

import groovy.json.JsonSlurper

import java.nio.file.Path

class JsonParser {//implements TemplateParser{

    /*String jsonFile

    JsonParser(String file){
        jsonFile = file
    }*/

    def parseAsFile(String jsonFile){
        //todo should not load from class when user forwards conifg file path
        String file = JsonParser.class.getClassLoader().getResource(jsonFile).getPath()
        new JsonSlurper().parseText(new File (file).text)
    }

    def parseAsStream(String jsonFile){
        //todo should not load from class when user forwards conifg file path
        InputStream stream = JsonParser.class.getClassLoader().getResourceAsStream(jsonFile)
        new JsonSlurper().parseText(stream.text)
    }
}
