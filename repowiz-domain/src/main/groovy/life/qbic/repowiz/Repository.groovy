package life.qbic.repowiz

class Repository {

    String name
    String repositoryType
    List<String> studyType
    boolean api
    List<String> minRequirements

    HashMap<String,String> characteristics


    Repository(String name,String repositoryType, List<String> studyType, boolean api, List<String> minimalRequirements){
        this.name = name
        this.repositoryType = repositoryType
        this.studyType = studyType
        this.api = api
        this.minRequirements = minimalRequirements
    }

    //add further repository characteristics if required
    void addCharacteristic(String key, String value){

    }

    //add getter and setter
}
