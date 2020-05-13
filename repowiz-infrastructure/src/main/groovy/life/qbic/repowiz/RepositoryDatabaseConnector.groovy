package life.qbic.repowiz

import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.spi.TargetRepository
import life.qbic.repowiz.spi.TargetRepositoryProvider
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

//todo also move repository descriptions to external repository
class RepositoryDatabaseConnector implements RepositoryDescription {

    String schema = "repositories/repository.schema.json"
    TargetRepository repository

    private static final Logger LOG = LogManager.getLogger(RepositoryDatabaseConnector.class)


    RepositoryDatabaseConnector(TargetRepository targetRepo) {
        repository = targetRepo
    }

    @Override
    Repository findRepository(String name) {

        List<TargetRepositoryProvider> providers = repository.providers()
        TargetRepositoryProvider targetProvider = null

        providers.each { provider ->
            if (provider.providerName.toLowerCase() == name) {
                targetProvider = provider
            }
        }

        if(targetProvider != null) return targetProvider.getRepositoryDescription()

       return null
    }

    Repository parseRepo(InputStream stream) {
        JsonParser parser = new JsonParser(stream)
        Map repositoryMap = parser.parse()
        parser.validate(schema, repositoryMap)

        RepositoryCreator creator = new RepositoryCreator(repositoryMap)

        return creator.create()
    }

    @Override
    List<Repository> findRepositories(List<String> repoNames) {
        List<Repository> repos = []
        repoNames.each { repo ->
            //match name to file
            Repository foundRepo = findRepository(repo)
            if (foundRepo != null) {
                repos << foundRepo
            }
        }
        if (repos.empty) {
            LOG.error "For the repositories $repoNames no submission support is provided!"
            System.exit(1)
        }
        return repos
    }

}
