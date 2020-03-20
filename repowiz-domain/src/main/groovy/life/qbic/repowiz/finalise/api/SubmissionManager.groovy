package life.qbic.repowiz.finalise.api

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface SubmissionManager {
    SubmissionModel getSubmissionModel(RepoWizProject project, List<RepoWizSample> samples)
    //service for retrieving the submission model = quote manager interface in baeldung example
}