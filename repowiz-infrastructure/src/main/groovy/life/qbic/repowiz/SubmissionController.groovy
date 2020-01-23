package life.qbic.repowiz

import groovy.json.JsonSlurper

class SubmissionController {

    String propertiesFile

    SubmissionController(String propertiesFile){ //see properties file
        this.propertiesFile = propertiesFile
    }

    def parse(){
        new JsonSlurper().parseText(new File(propertiesFile).text)
    }

    def chooseRepo(String repositoryName){
        repositoryName.toLowerCase()
    }

}
