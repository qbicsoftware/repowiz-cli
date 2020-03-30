package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

import java.lang.reflect.InvocationTargetException

interface Loader {

    List<TargetRepositoryProvider> load() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException, InvocationTargetException

    TargetRepositoryProvider load(String repositoryName) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException

}