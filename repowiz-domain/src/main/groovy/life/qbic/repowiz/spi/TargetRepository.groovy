package life.qbic.repowiz.spi

import java.nio.file.ProviderNotFoundException

/**
 * Defines a target repository in context of a repository that accepts scientific data
 *
 * This class should be used for describing a repository that accepts the submission data collected with RepoWiz
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class TargetRepository {
    Loader loader

    TargetRepository(Loader loader) {
        this.loader = loader
    }

    /**
     * Collects all {@link TargetRepositoryProvider} implementations
     * @return a list of all providers
     */
    List<TargetRepositoryProvider> providers() {
        List<TargetRepositoryProvider> services = loader.load()
        return services
    }

    /**
     * Collects a specific {@link TargetRepositoryProvider} based on the provider name
     * @param repositoryName specifying the repository provider
     * @return a instance of the defined {@link TargetRepositoryProvider}
     */
    TargetRepositoryProvider provider(String repositoryName) {

        TargetRepositoryProvider provider = loader.load(repositoryName)

        if (provider != null) return provider

        throw new ProviderNotFoundException("Target Repository provider " + provider.providerName + " not found")
    }
}
