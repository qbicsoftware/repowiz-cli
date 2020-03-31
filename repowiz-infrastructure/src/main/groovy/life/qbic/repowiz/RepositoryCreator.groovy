package life.qbic.repowiz

class RepositoryCreator {

    Repository createRepository(Map data){
        return new Repository(repositoryName: (String) data.get("repositoryName"),
                dataType: data.get("dataType"),
                uploadTypes: (List)data.get("uploadTypes"),
                uploadFormat: (String)data.get("uploadFormat"),
                uploadRequirements:(List)data.get("uploadRequirements"),
                characteristics: (HashMap)[size:(String)data.get("size")],
                subsequentSteps: (List) ["",""])
    }

}
