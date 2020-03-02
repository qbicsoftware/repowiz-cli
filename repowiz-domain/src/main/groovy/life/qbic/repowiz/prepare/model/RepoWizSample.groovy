package life.qbic.repowiz.prepare.model

class RepoWizSample {
    String sampleName
    HashMap<String,String> metaData

    RepoWizSample(String name, HashMap properties){
        sampleName = name
        metaData = properties
    }
}
