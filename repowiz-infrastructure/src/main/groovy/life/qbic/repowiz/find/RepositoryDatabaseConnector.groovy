package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription

class RepositoryDatabaseConnector implements RepositoryDescription{


    @Override
    List<Repository> findRepository(List<String> repositoryNames) {
        return null
    }

    @Override
    Repository findRepositoryByName(String repository) {
        return null
    }
}
