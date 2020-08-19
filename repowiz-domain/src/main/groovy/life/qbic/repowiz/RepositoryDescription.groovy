package life.qbic.repowiz

/**
 * Interface to delegate repository objects from the infrastructure into the core
 *
 * This class should be used to transfer information about repositories into a class
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface RepositoryDescription {

    /**
     * Searches for repository description for the given name and
     * creates a repository object for it (if found)
     *
     * @param repository name
     * @return repository object
     */
    Repository findRepository(String repoName)
    /**
     * Searches for all repositories in the list and
     * creates a repository object for each of them (if found)
     * @param repoNames
     * @return
     */
    List<Repository> findRepositories(List<String> repoNames)

}