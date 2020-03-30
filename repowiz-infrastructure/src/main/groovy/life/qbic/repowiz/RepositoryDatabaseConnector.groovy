package life.qbic.repowiz

import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.io.IO

//todo also move repository descriptions to external repository
class RepositoryDatabaseConnector implements RepositoryDescription{

    String path = "repositories/"
    JsonParser parser

    RepositoryDatabaseConnector(){
        parser = new JsonParser()
    }

    @Override
    List<Repository> findRepository(List<String> repositoryNames) {

        List<String> allRepositoryFiles = IO.getFilesFromDirectory(path)
        //println allRepositories.toString()

        List<Repository> repositories = []

        //create repository object from repository file
        allRepositoryFiles.each { fileName ->
            //get repo name
            String repo = fileName.split("\\.")[0]

            if(repositoryNames.contains(repo)){
                repositories << getRepository(path+fileName)
            }
        }

        return repositories
    }

    Repository getRepository(String fileURL){
        //String resourceFile1 = "repositories/geo.json"
        Repository repo = null

        InputStream resourceStream = RepositoryDatabaseConnector.class.getClassLoader().getResourceAsStream(fileURL)

        if (resourceStream != null) {
            def json = parser.parseAsStream(fileURL)
            assert json instanceof Map

            println json.get("repositoryName")
            repo = new Repository(repositoryName: (String) json.get("repositoryName"),
                    dataType: json.get("dataType"),
                    uploadTypes: (List)json.get("uploadTypes"),
                    uploadFormat: (String)json.get("uploadFormat"),
                    uploadRequirements:(List)json.get("uploadRequirements"),
                    characteristics: (HashMap)[size:(String)json.get("size")],
                    subsequentSteps: (List) ["",""])
        }

        return repo
    }


    /*Repository createRepoFromJSON(Map repoMap){

        Repository repo = new Repository(name: repoMap.get("repositoryName"),
                repositoryType: repoMap.get("repositoryName"),
                uploadTypes: repoMap.get("uploadTypes"),
                uploadFormat: repoMap.get("uploadFormat"),
                uploadRequirements:repoMap.get("uploadRequirements"),
                characteristics: [size:(String)repoMap.get("size")],
                subsequentSteps: ["",""])

        return repo
    }*/

}
