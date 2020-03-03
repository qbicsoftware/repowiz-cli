package life.qbic.repowiz.prepare.model

class RepoWizProject {
    String projectID
    HashMap<String,String> properties
    List<String> requiredFields //fields required in order to validate the object?
    List<RepoWizSample> samples = []

    RepoWizProject(String project, HashMap meta){
        projectID = project
        properties = meta
    }

    def addSample(RepoWizSample sample){
        samples << sample
    }

}
