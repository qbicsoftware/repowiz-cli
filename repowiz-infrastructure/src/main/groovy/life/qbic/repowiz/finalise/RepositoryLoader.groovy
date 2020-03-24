package life.qbic.repowiz.finalise

import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

import java.lang.reflect.Constructor

class RepositoryLoader implements Loader{
    String repositoryListFile = "services/life.qbic.repowiz.spi.TargetRepositoryProvider"

    List<TargetRepositoryProvider> load(){
        List<TargetRepositoryProvider> targetRepositories = []

        InputStream fileStream = RepositoryLoader.getClassLoader().getResourceAsStream(repositoryListFile)

        List providers = parseFileStream(fileStream)

        providers.each {provider ->
            targetRepositories << getClassInstance(provider)
        }

        return targetRepositories
    }

    static List parseFileStream(InputStream stream){
        List<String> provider = []
        stream.eachLine {targetRepository ->
            provider << targetRepository
        }
        return provider
    }

    static TargetRepositoryProvider getClassInstance(String repositoryProviderClass){
        Class targetClass = RepositoryLoader.getClassLoader().loadClass(repositoryProviderClass)
        Class<? extends TargetRepositoryProvider> providerImpl = targetClass.asSubclass(TargetRepositoryProvider.class)

        Constructor<? extends TargetRepositoryProvider> providerConstructor = providerImpl.getConstructor()
        return providerConstructor.newInstance()
    }

}
