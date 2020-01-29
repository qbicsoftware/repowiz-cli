package life.qbic.repowiz

class Repository {

    String name
    String repositoryType
    List<String> studyTypes
    String uploadType
    List<String> minRequirements //todo is that really a list or is other data structure more useful?

    HashMap<String,String> characteristics


    Repository(String name,String repositoryType, List<String> studyTypes, String uploadType, List<String> minimalRequirements){
        this.name = name
        this.repositoryType = repositoryType
        this.studyTypes = studyTypes
        this.uploadType = uploadType
        this.minRequirements = minimalRequirements
    }

    //add further repository characteristics if required
    void addCharacteristic(String key, String value){

    }

}
