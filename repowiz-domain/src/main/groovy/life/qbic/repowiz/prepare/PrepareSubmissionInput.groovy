package life.qbic.repowiz.prepare

import life.qbic.repowiz.Repository

/**
 * Interface defining what information flows into the user case
 *
 * This class should be used whenever information about the selected repository needs to be transferred into a class
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
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