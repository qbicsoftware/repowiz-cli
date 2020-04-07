package life.qbic.repowiz

class RepositoryCreator {
    private List requiredFields = ["repositoryName", "dataType","uploadTypes","uploadFormat",
                                    "uploadRequirements", "characteristics", "subsequentSteps"]


    Repository createRepository(Map data){
        return new Repository(repositoryName: (String) data.get("repositoryName"),
                dataType: data.get("dataType"),
                uploadTypes: (List)data.get("uploadTypes"),
                uploadFormat: (String)data.get("uploadFormat"),
                uploadRequirements:(List)data.get("uploadRequirements"),
                characteristics: (HashMap)[size:(String)data.get("size")],
                subsequentSteps: (List) ["",""])
    }

    def validate(Map data) throws Exception{
        //all fields are required in order to generate a valid repository


    }

}
