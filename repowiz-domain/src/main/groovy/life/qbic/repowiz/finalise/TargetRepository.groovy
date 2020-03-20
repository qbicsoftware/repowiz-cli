package life.qbic.repowiz.finalise

import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

import java.nio.file.ProviderNotFoundException

class TargetRepository {
    //utility class = exchange rate class in baeldung example
    //private static final String DEFAULT_PROVIDER = ""

    //All providers
    public static List<TargetRepositoryProvider> providers() {
        List<TargetRepositoryProvider> services = new ArrayList<>()
        ServiceLoader<TargetRepositoryProvider> loader = ServiceLoader.load(TargetRepositoryProvider.class)

        loader.forEach({ targetRepositoryProvider ->
            services.add(targetRepositoryProvider)
        })

        return services
    }

    /*//Default provider
    public static TargetRepositoryProvider provider() {
        return provider(DEFAULT_PROVIDER);
    }*/

    //provider by name
    public static TargetRepositoryProvider provider(String providerName) {
        ServiceLoader<TargetRepositoryProvider> loader = ServiceLoader.load(TargetRepositoryProvider.class)
        Iterator<TargetRepositoryProvider> it = loader.iterator()
        while (it.hasNext()) {
            TargetRepositoryProvider provider = it.next()
            if (providerName == provider.getClass().getName()) {
                return provider
            }
        }
        throw new ProviderNotFoundException("Target Repository provider " + providerName + " not found");
    }
}
