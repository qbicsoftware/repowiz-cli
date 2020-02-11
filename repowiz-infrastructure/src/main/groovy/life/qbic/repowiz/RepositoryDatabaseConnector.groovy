package life.qbic.repowiz


import life.qbic.repowiz.utils.IO

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
            def json = IO.parseJsonStream(resourceStream)
            assert json instanceof Map

            return createRepoFromJSON(json)
        }

        return null
    }


    Repository createRepoFromJSON(Map repoMap){
        String name = repoMap.get("repositoryName")
        String repositoryType = repoMap.get("repositoryName")
        List<String> experimentTypes = repoMap.get("experimentType")
        String uploadType = repoMap.get("uploadType")
        List<String> uploadRequirements = repoMap.get("uploadRequirements")
        String size = repoMap.get("size")

        Repository repo = new Repository(name,repositoryType,experimentTypes,uploadType,uploadRequirements)
        repo.addCharacteristic("size",size)

        return repo
    }
}
