package life.qbic.repowiz.find

import life.qbic.repowiz.Repository

/**
 * This interface defines what information leaves {@link FindMatchingRepositories}
 *
 * This interface should be used whenever information should be obtained from the {@link FindMatchingRepositories} class
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface MatchingRepositoriesOutput {

    /**
     * The answer choices for the next level in the tree are forwarded
     * @param list of choices
     */
    void transferAnswerPossibilities(List<String> choices)

    /**
     * The currently made decisions of the user are forwarded
     * @param decisions
     */
    void transferDecisionStack(List<String> decisions)

    /**
     * Information to be displayed to the user
     * @param info
     */
    void transferUserInformation(String info)

    /**
     * List of repositories from which the user must decide
     * @param repositories
     */
    void transferRepositoryList(List<Repository> repositories)

}