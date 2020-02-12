package life.qbic.repowiz.io

import groovy.json.JsonSlurper

class JsonParser implements TemplateParser{

    def static parseFile(File file){
        new JsonSlurper().parseText(file.text)
    }

    def static parseStream(InputStream file){
        new JsonSlurper().parseText(file.text)
    }
}
