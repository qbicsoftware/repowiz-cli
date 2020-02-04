package life.qbic.repowiz

class Repository {

    String name
    String repositoryType
    List<String> experimentTypes
    String uploadType
    List<String> uploadRequirements //todo is that really a list or is other data structure more useful?

    HashMap<String,String> characteristics
    ArrayList<String> subsequentSteps


    Repository(String name, String repositoryType, List<String> experimentTypes, String uploadType, List<String> uploadRequirements){
        this.name = name
        this.repositoryType = repositoryType
        this.experimentTypes = experimentTypes
        this.uploadType = uploadType
        this.uploadRequirements = uploadRequirements
    }

    //add further repository characteristics if required
    void addCharacteristic(String key, String value){

    }

}
