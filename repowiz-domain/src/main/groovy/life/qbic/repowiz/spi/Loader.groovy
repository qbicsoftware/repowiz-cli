package life.qbic.repowiz.spi

import life.qbic.repowiz.spi.TargetRepositoryProvider

import java.lang.reflect.InvocationTargetException

interface Loader {

    List<TargetRepositoryProvider> load() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException

    TargetRepositoryProvider load(String repositoryName) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException

}