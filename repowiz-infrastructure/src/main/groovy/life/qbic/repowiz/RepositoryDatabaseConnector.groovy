package life.qbic.repowiz

import life.qbic.repowiz.io.JsonParser

//todo also move repository descriptions to external repository
class RepositoryDatabaseConnector implements RepositoryDescription{

    String path //= "repositories"
    String schema
    List<String> validRepos

    RepositoryDatabaseConnector(String repositoryFolder, String repositorySchemaPath, List validRepos){
        path = repositoryFolder
        schema = repositorySchemaPath
        this.validRepos = validRepos
    }

    @Override
    Repository findRepository(String name){
        //String resourceFile1 = "repositories/geo.json"
        InputStream stream = RepositoryDatabaseConnector.class.getClassLoader().getResourceAsStream(path+"/"+getFileName(name))

        JsonParser parser = new JsonParser(stream)
        Map repositoryMap = parser.parse()
        parser.validate(schema,repositoryMap)

        RepositoryCreator creator = new RepositoryCreator(repositoryMap)

        return creator.create()
    }

    @Override
    List<Repository> findRepositories(List<String> repoNames) {
        List<Repository> repos = []
        repoNames.each {repo ->
            //match name to file
            String fileName = getFileName(repo)
            repos << findRepository(fileName)
        }
        return repos
    }

    private String getFileName(String repoName){
        String filePath = null

        validRepos.each{validRepo ->
            if(validRepo.contains(repoName)) filePath = validRepo
        }
        return filePath
    }
}
