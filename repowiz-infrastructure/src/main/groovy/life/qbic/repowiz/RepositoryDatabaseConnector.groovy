package life.qbic.repowiz

import life.qbic.repowiz.io.JsonParser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

//todo also move repository descriptions to external repository
class RepositoryDatabaseConnector implements RepositoryDescription{

    String path //= "repositories"
    String schema
    List<String> validRepos

    private static final Logger LOG = LogManager.getLogger(RepositoryDatabaseConnector.class)


    RepositoryDatabaseConnector(String repositoryFolder, String repositorySchemaPath, List validRepoFileNames){
        path = repositoryFolder
        schema = repositorySchemaPath
        this.validRepos = validRepoFileNames
    }

    @Override
    Repository findRepository(String name){
        //String resourceFile1 = "repositories/geo.json"
        String fileName = getFileName(name)
        if (fileName != null){
            InputStream stream = RepositoryDatabaseConnector.class.getClassLoader().getResourceAsStream(path+"/"+fileName)

            JsonParser parser = new JsonParser(stream)
            Map repositoryMap = parser.parse()
            parser.validate(schema,repositoryMap)

            RepositoryCreator creator = new RepositoryCreator(repositoryMap)

            return creator.create()
        }else {
            LOG.error "No repository description (json file) was found for $name"
            return null
        }
    }

    @Override
    List<Repository> findRepositories(List<String> repoNames) {
        List<Repository> repos = []
        repoNames.each {repo ->
            //match name to file
            //String fileName = getFileName(repo)
            Repository foundRepo = findRepository(repo)
            if(foundRepo != null){
                repos << foundRepo
            }
        }
        if(repos.empty){
            LOG.error "For the repositories $repoNames no submission support is provided!"
            System.exit(1)
        }
        return repos
    }

    private String getFileName(String repoName){
        String filePath = null

        validRepos.each{validRepo ->
            if(validRepo.contains(repoName) || validRepo.toLowerCase().contains(repoName.toLowerCase())) {
                filePath = validRepo
            }
        }
        return filePath
    }
}
