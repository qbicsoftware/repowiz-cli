package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider
import org.apache.commons.lang.StringUtils

import java.lang.reflect.Constructor

class RepositoryLoader implements Loader{

    String repositoryListFile = "META_INF/services/life.qbic.repowiz.spi.TargetRepositoryProvider"

    List<TargetRepositoryProvider> load(){
        List<TargetRepositoryProvider> targetRepositories = []

        InputStream fileStream = RepositoryLoader.getClassLoader().getResourceAsStream(repositoryListFile)

        List providers = parseFileStream(fileStream)

        providers.each {provider ->
            targetRepositories << getClassInstance(provider)
        }

        return targetRepositories
    }

    TargetRepositoryProvider load(Repository repository){

        TargetRepositoryProvider targetRepository = null

        InputStream fileStream = RepositoryLoader.getClassLoader().getResourceAsStream(repositoryListFile)

        List providers = parseFileStream(fileStream)

        providers.each {provider ->
            if(provider == repository.name || StringUtils.lowerCase(provider).contains(provider)) targetRepository = getClassInstance(provider)
            //todo how to compare the names? what do i accept and what not
        }

        return targetRepository
    }

    //extract all provider from file
    static List parseFileStream(InputStream stream){
        List<String> provider = []

        stream.eachLine {targetRepository ->
            provider << targetRepository
        }

        return provider
    }

    //create instance of defined provider class
    static TargetRepositoryProvider getClassInstance(String repositoryProviderClass){

        Class targetClass = RepositoryLoader.getClassLoader().loadClass(repositoryProviderClass)
        Class<? extends TargetRepositoryProvider> providerImpl = targetClass.asSubclass(TargetRepositoryProvider.class)

        Constructor<? extends TargetRepositoryProvider> providerConstructor = providerImpl.getConstructor()

        return providerConstructor.newInstance()
    }

}
