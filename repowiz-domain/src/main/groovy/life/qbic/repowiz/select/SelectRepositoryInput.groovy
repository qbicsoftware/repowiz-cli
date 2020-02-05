package life.qbic.repowiz.select

import life.qbic.repowiz.Repository

interface SelectRepositoryInput {

    def selectRepository(String repository)
    def suggestedRepository(Repository suggestedRepos)

}