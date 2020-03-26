package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

interface Loader {

    List<TargetRepositoryProvider> load()
    TargetRepositoryProvider load(String repositoryName)

}