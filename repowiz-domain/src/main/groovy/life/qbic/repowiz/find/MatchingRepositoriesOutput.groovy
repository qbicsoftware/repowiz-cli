package life.qbic.repowiz.find

import life.qbic.repowiz.Repository

/**
 * This interface defines what information is leaves FindMatchingRepositories
 */
interface MatchingRepositoriesOutput {

    /**
     * The answer choices for the next level in the tree are forwarded
     * @param list of choices
     * @return -
     */
    def transferAnswerPossibilities(List<String> choices)

    /**
     * The currently made decisions of the user are forwarded
     * @param decisions
     * @return
     */
    def transferDecisionStack(List<String> decisions)

    /**
     * Information to be displayed to the user
     * @param info
     * @return
     */
    def transferUserInformation(String info)

    /**
     * List of repositories from which the user must decide
     * @param repositories
     * @return
     */
    def transferRepositoryList(List<Repository>repositories)

}