package life.qbic.impl

import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.api.SubmissionModel
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

class GeoSubmissionManager implements SubmissionManager{
    @Override
    SubmissionModel getSubmissionModel(RepoWizProject project, List<RepoWizSample> samples) {
        return null
    }
}
