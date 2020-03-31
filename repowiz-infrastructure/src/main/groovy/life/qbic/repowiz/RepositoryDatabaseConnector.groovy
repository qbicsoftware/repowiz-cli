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
        def repositoryMap = parser.getMapFromJsonFile(fileURL)

        RepositoryCreator creator = new RepositoryCreator()

        return creator.createRepository(repositoryMap)
    }


}
