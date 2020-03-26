package life.qbic.repowiz.model

class SubmissionModel {
    RepoWizProject project
    List<RepoWizSample> samples
    List<String> missingValues
    String uploadType

    SubmissionModel(RepoWizProject project, List<RepoWizSample> samples){
        this.project = project
        this.samples = samples
    }

}
