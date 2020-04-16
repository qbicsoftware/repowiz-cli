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

    HashMap getAllProperties() {
        HashMap properties = project.properties

        samples.each {sample ->
            properties << sample.properties
        }
        return properties
    }

}
