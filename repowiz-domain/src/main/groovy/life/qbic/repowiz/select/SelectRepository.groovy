package life.qbic.repowiz.select

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription

class SelectRepository implements SelectRepositoryInput {

    RepositoryDescription repositoryDescription
    SelectRepositoryOutput output

    SelectRepository(SelectRepositoryOutput output, RepositoryDescription description) {
        repositoryDescription = description
        this.output = output
    }

    @Override
    def selectRepository(String repository) {
        //check if repository is in database
        Repository selectedRepository = repositoryDescription.findRepository([repository]).get(0)

        if(selectedRepository != null){
            //forward valid repo to output
            output.selectedRepository(selectedRepository)
            return true
        }
        else{
            println "No valid repository was selected"
            return false
        }
    }

    @Override
    def selectRepoFromSuggestions(List<Repository> suggestedRepos) {
        //validate if repository can be selected?
        List<String> nameList = getRepositoryNameList(suggestedRepos)

        String user_choice = output.chooseRepository(nameList)

        output.selectedRepository(findMatchingRepository(user_choice,suggestedRepos))

    }

    List<String> getRepositoryNameList(List<Repository> suggestedRepo){
        List<String> repoNames = []
        suggestedRepo.each {
            repoNames << it.name
        }
        return repoNames
    }

    def findMatchingRepository(String user_choice, List<Repository> suggested){
        Repository validChoice = null

        suggested.each {
            if(it.name == user_choice){
               validChoice = it
            }
        }

        return validChoice
    }
}