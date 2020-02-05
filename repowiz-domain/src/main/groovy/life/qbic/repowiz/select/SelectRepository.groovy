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
        //check if repository is in database
        Repository selectedRepository = repositoryDescription.findRepository([repository]).get(0)

        //forward valid repo to output
        output.selectedRepository(selectedRepository)
    }

    @Override
    def suggestedRepository(Repository suggestedRepo) {
        output.selectedRepository(suggestedRepo)
    }
}