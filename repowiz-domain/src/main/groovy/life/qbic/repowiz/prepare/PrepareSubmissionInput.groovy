package life.qbic.repowiz.prepare

import life.qbic.repowiz.Repository

/**
 * Interface defining what information flows into the user case
 */
interface PrepareSubmissionInput {

    /**
     * Triggers the preparation of the submission for the given repository
     * @param repository
     * @return
     */
    def prepareSubmissionToRepository(Repository repository)

    /**
     * Sets the upload type for the submission
     * @param answer
     * @return
     */
    def setUploadType(String answer)
}