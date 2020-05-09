package life.qbic.repowiz.select

import life.qbic.repowiz.Repository

/**
 * Interface defining the information that flows from SelectRepository towards an implementing class
 */
interface SelectRepositoryOutput {

    /**
     * Repository object of the selected repository
     * @param repository
     * @return
     */
    def selectedRepository(Repository repository)

    /**
     * A list of repositories from which the user must choose one
     * @param repositories
     * @return
     */
    def chooseRepository(List<String> repositories)

}