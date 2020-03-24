package life.qbic.repowiz.finalise

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
        List<TargetRepositoryProvider> services = new ArrayList<>()


        /*//use the service loader to
        ServiceLoader<TargetRepositoryProvider> loader = ServiceLoader.load(TargetRepositoryProvider.class)

        loader.forEach({ targetRepositoryProvider ->
            services.add(targetRepositoryProvider)
        })*/

        return services
    }

    //provider by name
    TargetRepositoryProvider provider(String providerName) {

        /*ServiceLoader<TargetRepositoryProvider> loader = ServiceLoader.load(TargetRepositoryProvider.class)
        Iterator<TargetRepositoryProvider> it = loader.iterator()
        while (it.hasNext()) {
            TargetRepositoryProvider provider = it.next()
            if (providerName == provider.getClass().getName()) {
                return provider
            }
        }

        throw new ProviderNotFoundException("Target Repository provider " + providerName + " not found");*/
        return null
    }
}
