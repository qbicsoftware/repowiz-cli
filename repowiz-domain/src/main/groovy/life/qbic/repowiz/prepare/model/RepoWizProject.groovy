package life.qbic.repowiz.prepare.model

class RepoWizProject {
    String projectID
    HashMap<String,String> metadata
    List<String> requiredFields //fields required in order to validate the object?
    List<RepoWizSample> samples = []

    RepoWizProject(String project, HashMap meta){
        projectID = project
        metadata = meta
    }

    def addSample(RepoWizSample sample){
        samples << sample
    }

}
