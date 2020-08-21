package life.qbic.repowiz

import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.spi.TargetRepository
import life.qbic.repowiz.spi.TargetRepositoryProvider
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * This class connects RepoWiz with the repositories (repository database)
 *
 * This class should be used whenever it is searched for a repository and its properties
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class RepositoryDatabaseConnector implements RepositoryDescription {

    private final String schema = "repositories/repository.schema.json"
    private final TargetRepository repository

    private static final Logger LOG = LogManager.getLogger(RepositoryDatabaseConnector.class)


    /**
     * Creates a connector to the data of a given repository
     * @param targetRepo describes the repository for which further information needs to be obtained
     */
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

        if (targetProvider != null) return targetProvider.getRepositoryDescription()

        return null
    }

    /**
     * Parses information of a repository given by an input stream. The data is used to create a {@link Repository} DTO
     *
     * @param stream containing information about a repository
     * @return a DTO containing information of the repository
     */
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
