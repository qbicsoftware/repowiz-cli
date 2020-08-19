package life.qbic.repowiz.select

import life.qbic.repowiz.Repository

/**
 * Interface to define the input of {@link SelectRepository}
 *
 * Should be used whenever a selected repository should be the input of a class
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface SelectRepositoryInput {

    /**
     * The selected repository name is forwarded to the use case
     * Direct selection of the repository to which an upload is desired
     *
     * @param repository
     * @return
     */
    def selectRepository(String repository)

    /**
     * A list of repositories suggested for a submission
     * User must first select one of them before a submission is created
     *
     * @param suggestedRepos
     * @return
     */
    def selectRepoFromSuggestions(List<Repository> suggestedRepos)

    /**
     * Check if the selected user answer is valid (was suggested to him)
     * @param answer
     * @return
     */
    def validateSelectedRepository(String answer)

}