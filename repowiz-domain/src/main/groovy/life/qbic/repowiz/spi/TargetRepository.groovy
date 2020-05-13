package life.qbic.repowiz.spi


import java.nio.file.ProviderNotFoundException

class TargetRepository {
    //utility class = exchange rate class in baeldung example
    Loader loader

    TargetRepository(Loader loader){
        this.loader = loader
    }

   /* public static List<TargetRepositoryProvider> providers() {
        List<TargetRepositoryProvider> services = new ArrayList<>()
        ServiceLoader<TargetRepositoryProvider> loader = ServiceLoader.load(TargetRepositoryProvider.class)

        loader.each{targetRepositoryProvider ->
            services.add(targetRepositoryProvider)
        }

        return services
    }

    //provider by name
    public static TargetRepositoryProvider provider(String providerName) {
        ServiceLoader<TargetRepositoryProvider> loader = ServiceLoader.load(TargetRepositoryProvider.class);
        //Iterator<TargetRepositoryProvider> it = loader.iterator();
        loader.each {
            TargetRepositoryProvider provider = it
            println it.providerName
            if (providerName.contains(provider.getClass().getName())) {
                return provider
            }
        }
        throw new ProviderNotFoundException("Target Repository provider " + providerName + " not found");
    }*/


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
