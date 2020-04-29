package life.qbic.repowiz.model

class RepoWizSample {
    String sampleName
    HashMap<String,String> properties
    //label:value
    HashMap<String,String> characteristics
    List<String> rawFiles

    RepoWizSample(String name, HashMap properties){
        sampleName = name
        this.properties = properties
    }

    //todo do not implement any logic in here!!!!
}
