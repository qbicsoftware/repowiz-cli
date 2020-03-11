package life.qbic.repowiz.prepare.model

class RepoWizProject {

    String projectID
    HashMap<String,String> properties

    RepoWizProject(String project, HashMap meta){
        projectID = project
        properties = meta
    }

    //todo do not implement any logic in here!!!!
}
