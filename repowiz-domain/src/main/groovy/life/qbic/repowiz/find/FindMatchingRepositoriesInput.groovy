package life.qbic.repowiz.find

/**
 * Input interface determining what information is accepted by FindMatchingRepositories
 */
interface FindMatchingRepositoriesInput {

    /**
     * This method starts the traversal of the decision tree from the root
     * @return
     */
    def startGuide()

    /**
     * This method matches the answer of the user with the decision tree and if valid
     * proceeds to the next tree node
     * @param answer
     * @return
     */
    def validateDecision(String answer)
    //def suggestRepos(HashMap<String,String> submissionSpecification)

}