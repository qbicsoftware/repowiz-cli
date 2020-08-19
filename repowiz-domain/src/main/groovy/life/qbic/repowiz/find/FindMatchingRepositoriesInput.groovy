package life.qbic.repowiz.find

/**
 * Input interface determining what information is accepted by FindMatchingRepositories
 *
 * This interface should be used whenever the {@link FindMatchingRepositories} class should be started for finding a matching repository for the user
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