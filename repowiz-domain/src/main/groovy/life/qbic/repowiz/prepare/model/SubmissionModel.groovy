package life.qbic.repowiz.prepare.model

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

class SubmissionModel {
    RepoWizProject project
    List<RepoWizSample> samples
    List<String> missingValues

    SubmissionModel(RepoWizProject project, List<RepoWizSample> samples){
        this.project = project
        this.samples = samples
    }

}
