package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.model.SubmissionModel

interface FinaliseSubmission {
    def transferSubmissionData(SubmissionModel submission, Repository repository)
    def processVerificationOfSubmission(boolean userAnswer)
}