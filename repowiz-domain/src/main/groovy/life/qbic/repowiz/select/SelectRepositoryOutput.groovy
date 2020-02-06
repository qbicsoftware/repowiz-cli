package life.qbic.repowiz.select

import life.qbic.repowiz.Repository

interface SelectRepositoryOutput {

    def selectedRepository(Repository repository)
    String chooseRepository(List<String> repositories)

}