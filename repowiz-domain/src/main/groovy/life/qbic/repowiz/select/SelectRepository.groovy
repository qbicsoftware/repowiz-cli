package life.qbic.repowiz.select

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription

class SelectRepository implements SelectRepositoryInput {

    RepositoryDescription repositoryDescription
    SelectRepositoryOutput output

    SelectRepository(SelectRepositoryOutput output, RepositoryDescription description) {
        repositoryDescription = description
        this.output = output
    }

    @Override
    def selectRepository(String repository) {
        return null
    }

    @Override
    boolean suggestedRepository(List<Repository> suggestedRepos) {
        return null
    }
}