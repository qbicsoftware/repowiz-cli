package life.qbic.repowiz.select

import life.qbic.repowiz.Repository

/**
 * Interface defining the information that flows from {@link SelectRepository} towards an implementing class
 *
 * This class should be used whenever information about the selected repository needs to be transferred out of the class
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
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