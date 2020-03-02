package life.qbic.repowiz.prepare.model

class RepoWizProject {
    String projectID
    HashMap<String,String> metadata
    List<String> requiredFields //fields required in order to validate the object?
    List<RepoWizExperiment> experiments

    RepoWizProject(String project){
        projectID = project
    }

}
