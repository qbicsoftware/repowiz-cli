package life.qbic.repowiz.select

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class SelectRepository implements SelectRepositoryInput{

    RepositoryDescription repositoryDescription
    SelectRepositoryOutput output

    List<Repository> suggestedRepos

    private static final Logger LOG = LogManager.getLogger(SelectRepository.class)

    SelectRepository(SelectRepositoryOutput output, RepositoryDescription description) {
        repositoryDescription = description
        this.output = output
    }

    @Override
    def selectRepository(String repository) {
        //check if repository is in database
        Repository selectedRepository = repositoryDescription.findRepository(repository)

        if(selectedRepository != null){
            //forward valid repo to output
            output.selectedRepository(selectedRepository)
            return true
        }
        else{
            LOG.error "No valid repository was selected"
            System.exit(-1) //do that this way? which code to use?
            return false
        }
    }

    @Override
    def selectRepoFromSuggestions(List<Repository> suggestedRepos) {
        //validate if repository can be selected?
        List<String> nameList = getRepositoryNameList(suggestedRepos)

        this.suggestedRepos = suggestedRepos

        output.chooseRepository(nameList)
    }

    @Override
    def validateSelectedRepository(String answer) {
        Repository repo = getValidRepository(answer)

        if(repo != null){
            output.selectedRepository(repo)
        }
        else {
            LOG.error "No valid repository was selected"
            System.exit(-1)
        }
    }

    List<String> getRepositoryNameList(List<Repository> suggestedRepo){
        List<String> repoNames = []
        suggestedRepo.each {
            repoNames << it.repositoryName
        }
        return repoNames
    }

    Repository getValidRepository(String user_choice){
        Repository validChoice = null

        suggestedRepos.each {
            if(it.repositoryName.toLowerCase() == user_choice.toLowerCase()){
                validChoice = it
            }
        }

        return validChoice
    }
}