package life.qbic.repowiz

class RepositoryCreator {
    Map data

    RepositoryCreator(Map data){
        this.data = data
    }

    Repository create(){
        return new Repository(repositoryName: (String) data.get("repositoryName"),
                dataType: data.get("dataType"),
                uploadTypes: (List)data.get("uploadTypes"),
                uploadFormat: (String)data.get("uploadFormat"),
                uploadRequirements:(List)data.get("uploadRequirements"),
                characteristics: (HashMap)[size:(String)data.get("size")],
                subsequentSteps: (List) ["",""])
    }


}
