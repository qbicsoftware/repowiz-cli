package life.qbic.repowiz

import groovy.transform.MapConstructor

@MapConstructor
class Repository {
    String repositoryName
    String dataType
    List<String> uploadTypes
    String uploadFormat
    List<String> uploadRequirements //todo is that really a list or is other data structure more useful?
    HashMap<String,String> characteristics
    ArrayList<String> subsequentSteps
}
