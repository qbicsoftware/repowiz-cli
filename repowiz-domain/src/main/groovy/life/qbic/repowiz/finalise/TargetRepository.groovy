package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

import java.nio.file.ProviderNotFoundException

class TargetRepository {
    //utility class = exchange rate class in baeldung example
    Loader loader

    TargetRepository(Loader loader){
        this.loader = loader
    }

    //All providers
    List<TargetRepositoryProvider> providers() {
        List<TargetRepositoryProvider> services = loader.load()
        return services
    }

    //provider by concrete repository
    TargetRepositoryProvider provider(String repositoryName) {

        TargetRepositoryProvider provider = loader.load(repositoryName)

        if(provider != null) return provider

        throw new ProviderNotFoundException("Target Repository provider " + provider.providerName + " not found")
    }
}
