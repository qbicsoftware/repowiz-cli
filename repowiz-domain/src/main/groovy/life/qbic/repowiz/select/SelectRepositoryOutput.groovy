package life.qbic.repowiz.select

import life.qbic.repowiz.Repository

interface SelectRepositoryOutput {

    def selectedRepository(Repository repository)
    def chooseRepository(List<String> repositories)

}