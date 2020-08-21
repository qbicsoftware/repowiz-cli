package life.qbic.repowiz.spi

import java.lang.reflect.InvocationTargetException

/**
 * Interface defining how a {@link TargetRepositoryProvider} is loaded
 *
 * This class should be used for loading the respective {@link TargetRepositoryProvider} class instances
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface Loader {

    /**
     * Load all implementations of the SPI and return them as list
     * @return
     * @throws IOException
     * @throws ClassNotFoundException* @throws NoSuchMethodException @throws InvocationTargetException @throws InstantiationException @throws IllegalAccessException @throws InvocationTargetException @throws InvocationTargetException @throws InvocationTargetException @throws InvocationTargetException @throws InvocationTargetException
     */
    List<TargetRepositoryProvider> load() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException

    /**
     * Load the provider for the given name and return the TargetRepositoryProvider object
     * @param repositoryName
     * @return
     * @throws IOException
     * @throws ClassNotFoundException* @throws NoSuchMethodException @throws InvocationTargetException @throws InstantiationException @throws IllegalAccessException
     */
    TargetRepositoryProvider load(String repositoryName) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException

}