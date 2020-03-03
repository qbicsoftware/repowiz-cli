package life.qbic.repowiz.prepare.model

class RepoWizSample {
    String sampleName
    HashMap<String,String> properties

    RepoWizSample(String name, HashMap properties){
        sampleName = name
        this.properties = properties
    }
}
