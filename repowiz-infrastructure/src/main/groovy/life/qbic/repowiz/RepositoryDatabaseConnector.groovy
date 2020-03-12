package life.qbic.repowiz

import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.io.IO

//todo also move repository descriptions to external repository
class RepositoryDatabaseConnector implements RepositoryDescription{

    String path = "repositories/"

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

        InputStream resourceStream = RepositoryDatabaseConnector.class.getClassLoader().getResourceAsStream(fileURL)

        if (resourceStream != null) {
            JsonParser parser = new JsonParser()
            def json = parser.parseAsStream(fileURL)
            assert json instanceof Map

            return createRepoFromJSON(json)
        }

        return null
    }


    Repository createRepoFromJSON(Map repoMap){
        String name = repoMap.get("repositoryName")
        String repositoryType = repoMap.get("repositoryName")
        List<String> uploadTypes = repoMap.get("uploadTypes")
        String uploadFormat = repoMap.get("uploadFormat")
        List<String> uploadRequirements = repoMap.get("uploadRequirements")
        String size = repoMap.get("size")

        Repository repo = new Repository(name,repositoryType,uploadTypes,uploadFormat,uploadRequirements)
        repo.addCharacteristic("size",size)

        return repo
    }
}
