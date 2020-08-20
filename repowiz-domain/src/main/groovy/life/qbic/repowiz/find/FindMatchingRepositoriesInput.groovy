package life.qbic.repowiz.find

/**
 * Input interface determining what information is accepted by FindMatchingRepositories
 *
 * Classes implementing this interface offer functionality to search for repositories filtered by provided parameters.
 * @see FindMatchingRepositories
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface FindMatchingRepositoriesInput {

    /**
     * This method starts the traversal of the decision tree from the root
     */
    void startGuide()

    /**
     * This method matches the answer of the user with the decision tree and if valid
     * proceeds to the next tree node
     * @param answer given by the user
     */
    boolean validateDecision(String answer)

}
