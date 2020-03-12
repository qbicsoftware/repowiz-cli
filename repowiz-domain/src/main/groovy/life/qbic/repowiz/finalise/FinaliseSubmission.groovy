package life.qbic.repowiz.finalise

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface FinaliseSubmission {

    def transferSubmissionData(RepoWizProject project, List<RepoWizSample> samples)
    def setSubmissionDetails(String repositoryName, String uploadType)
}