package life.qbic.repowiz

class Repository {

    String name
    String repositoryType
    List<String> uploadTypes
    String uploadFormat
    List<String> uploadRequirements //todo is that really a list or is other data structure more useful?
    String selectedUploadType

    HashMap<String,String> characteristics
    ArrayList<String> subsequentSteps


    Repository(String name, String repositoryType, List<String> uploadTypes, String uploadFormat, List<String> uploadRequirements){
        this.name = name
        this.repositoryType = repositoryType
        this.uploadTypes = uploadTypes
        this.uploadFormat = uploadFormat
        this.uploadRequirements = uploadRequirements
    }

    //add further repository characteristics if required
    void addCharacteristic(String key, String value){

    }

}
