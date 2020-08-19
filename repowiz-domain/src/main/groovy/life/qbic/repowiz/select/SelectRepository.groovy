package life.qbic.repowiz.select

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Defines how a repository can be selected
 *
 * This class should be used whenever a repository needs to be selected. The selection is crucial for subsequent steps
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class SelectRepository implements SelectRepositoryInput {

    private final RepositoryDescription repositoryDescription
    private final SelectRepositoryOutput output

    List<Repository> suggestedRepos

    private static final Logger LOG = LogManager.getLogger(SelectRepository.class)

    /**
     * Creates a SelectRepository object
     * @param output defines how data can be transferred out of this class
     * @param description describes repository traists and characteristics
     */
    SelectRepository(SelectRepositoryOutput output, RepositoryDescription description) {
        repositoryDescription = description
        this.output = output
    }

    @Override
    def selectRepository(String repository) {
        //check if repository is in database
        Repository selectedRepository = repositoryDescription.findRepository(repository)

        if (selectedRepository != null) {
            //forward valid repo to output
            output.selectedRepository(selectedRepository)
            return true
        } else {
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

        if (repo != null) {
            output.selectedRepository(repo)
        } else {
            LOG.error "No valid repository was selected"
            System.exit(-1)
        }
    }

    /**
     * Retrieves a list with the repository names
     * @param suggestedRepo is a list of suggested repositories
     * @return a list of the names of the suggested repositories
     */
    List<String> getRepositoryNameList(List<Repository> suggestedRepo) {
        List<String> repoNames = []
        suggestedRepo.each {
            repoNames << it.repositoryName
        }
        return repoNames
    }

    /**
     * Checks if the given choice fits the repositories from which he can choose
     * @param user_choice is the choice of a user
     * @return a repository if the choice is valid or else null
     */
    Repository getValidRepository(String user_choice) {
        Repository validChoice = null

        suggestedRepos.each {
            if (it.repositoryName.toLowerCase() == user_choice.toLowerCase()) {
                validChoice = it
            }
        }
        return validChoice
    }
}