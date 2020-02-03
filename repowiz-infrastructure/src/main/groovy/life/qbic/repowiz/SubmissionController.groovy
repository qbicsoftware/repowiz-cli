package life.qbic.repowiz

import groovy.json.JsonSlurper
import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.utils.IO

class SubmissionController {

    String propertiesFile
    FindMatchingRepositoriesInput findRepoInput

    SubmissionController(String propertiesFile, FindMatchingRepositoriesInput findRepoInput){ //see properties file
        //this.propertiesFile = propertiesFile
        this.findRepoInput = findRepoInput
    }

    def parse(){
        IO.parseJSON(new File(propertiesFile))
    }

    def chooseRepo(String repositoryName){
        repositoryName.toLowerCase()
    }

    def findRepository(){
        findRepoInput.startGuide()
    }

}
