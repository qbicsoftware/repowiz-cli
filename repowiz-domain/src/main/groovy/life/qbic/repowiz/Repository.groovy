package life.qbic.repowiz

import groovy.transform.MapConstructor

@MapConstructor
class Repository {
    //the map constructor annotation creates a constructor that accepts a map and fills the above variables from the map
    String repositoryName
    String dataType
    List<String> uploadTypes
    String uploadFormat
    List<String> uploadRequirements
    ArrayList<String> subsequentSteps
}
