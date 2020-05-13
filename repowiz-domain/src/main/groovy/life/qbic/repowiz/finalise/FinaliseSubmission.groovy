package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.model.SubmissionModel

/**
 * Interface to define what information is required to finalise the submission
 */
interface FinaliseSubmission {

    /**
     * Injects the submission model with the submission informatino and the repository object
     * of the target repository to transfer the data to provider of this repository
     *
     * @param submission
     * @param repository
     * @return
     */
    def transferSubmissionData(SubmissionModel submission, Repository repository)

    /**
     * Process if the submission is verified by the user and download the submission.
     * If not, stop the process.
     *
     * @param userAnswer
     * @return
     */
    def processVerificationOfSubmission(boolean userAnswer)
}