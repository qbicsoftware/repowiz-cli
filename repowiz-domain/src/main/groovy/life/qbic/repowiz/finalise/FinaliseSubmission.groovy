package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import life.qbic.repowiz.prepare.model.SubmissionModel

interface FinaliseSubmission {
    def transferSubmissionData(SubmissionModel submission, Repository repository)
    def processVerificationOfSubmission(boolean userAnswer)
}