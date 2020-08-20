package life.qbic.repowiz.application.spi;

import life.qbic.repowiz.spi.Loader;
import life.qbic.repowiz.spi.TargetRepositoryProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for loading repositories in order to be accessible for RepoWiz
 *
 * It should be used every time a repository class is loaded from any maven module.
 *
 * @since: 1.0.0
 * @author: Jennifer Bödker
 *
 */
public class RepositoryLoaderJava implements Loader {

    private static final Logger LOG = LogManager.getLogger(RepositoryLoaderJava.class);
    private String repositoryListFile = "services/life.qbic.repowiz.spi.TargetRepositoryProvider.txt";

    /**
     * Extracts all implementations of the {@link TargetRepositoryProvider}
     * @param stream of a file that needs to be parsed
     * @return list of repository providers
     * @throws IOException
     */
    private static List<String> parseFileStream(InputStreamReader stream) throws IOException {
        LOG.info("Load provider specification list");

        List<String> provider = new ArrayList<>();

        BufferedReader reader = new BufferedReader(stream);

        while (reader.ready()) {
            String targetRepository = reader.readLine();
            LOG.debug("Found provider: " + targetRepository);
            provider.add(targetRepository);
        }

        return provider;
    }

    /**
     * Loads all classes that implement the {@link TargetRepositoryProvider} from a list in {@link #repositoryListFile}.
     * @return a list of repository instances
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public List<TargetRepositoryProvider> load() throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        List<TargetRepositoryProvider> targetRepositories = new ArrayList<>();
        LOG.info("Loads all target repositories ");

        InputStreamReader fileStream = new InputStreamReader(RepositoryLoaderJava.class.getClassLoader().getResourceAsStream(repositoryListFile));

        List<String> providers = parseFileStream(fileStream);

        for (String provider : providers) {
            targetRepositories.add(getClassInstance(provider));
        }

        LOG.info("Successfully loaded all target repositories ");

        return targetRepositories;
    }

    @Override
    public TargetRepositoryProvider load(String repositoryName) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        TargetRepositoryProvider targetRepository = null;
        LOG.info("Load target repository " + repositoryName);

        InputStreamReader fileStream = new InputStreamReader(RepositoryLoaderJava.class.getClassLoader().getResourceAsStream(repositoryListFile));

        List<String> providers = parseFileStream(fileStream);

        for (String provider : providers) {
            LOG.debug("Provider: " + provider + " compared to " + repositoryName);
            String test = StringUtils.lowerCase(provider);
            LOG.debug(test);
            if (provider.equals(StringUtils.lowerCase(repositoryName)) || test.contains(StringUtils.lowerCase(repositoryName) + "targetrepositoryprovider")) {
                targetRepository = getClassInstance(provider);
                LOG.info("Successfully loaded target repository " + repositoryName);
            }
        }

        return targetRepository;
    }

    /**
     * Creates an instance of provider class defined as a string
     * @param repositoryProviderClass defines the class to be instantiated
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public TargetRepositoryProvider getClassInstance(String repositoryProviderClass) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        LOG.info("Create Repository Instance");

        Class targetClass = RepositoryLoaderJava.class.getClassLoader().loadClass(repositoryProviderClass);
        Class<? extends TargetRepositoryProvider> providerImpl = targetClass.asSubclass(TargetRepositoryProvider.class);

        Constructor<? extends TargetRepositoryProvider> providerConstructor = providerImpl.getConstructor();

        return providerConstructor.newInstance();
    }
}
