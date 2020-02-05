package life.qbic.repowiz


import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.utils.IO

class SubmissionController {

    String propertiesFile
    FindMatchingRepositoriesInput findRepoInput
    SelectRepositoryInput selectRepositoryInput

    SubmissionController(String propertiesFile, FindMatchingRepositoriesInput findRepoInput){ //see properties file
        //this.propertiesFile = propertiesFile
        this.findRepoInput = findRepoInput
    }

    SubmissionController(String propertiesFile, SelectRepositoryInput selectRepositoryInput){ //see properties file
        //this.propertiesFile = propertiesFile
        this.selectRepositoryInput = selectRepositoryInput
    }

    def parse(){
        IO.parseJsonFile(new File(propertiesFile))
    }

    def chooseRepo(String repositoryName){
        selectRepositoryInput.selectRepository(repositoryName.toLowerCase())
    }

    def findRepository(){
        findRepoInput.startGuide()
    }

}
