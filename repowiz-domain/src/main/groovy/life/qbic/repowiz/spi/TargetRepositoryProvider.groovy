package life.qbic.repowiz.spi

import life.qbic.repowiz.Repository

/**
 * SPI interface that determines the functions a repository provider must have
 *
 * This class defines how a repository provider should look like in order to be supported by RepoWiz. 
 * This class has to be implemented by all repository providers in order for them to be found by RepoWiz!
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
abstract class TargetRepositoryProvider {

    /**
     * Returns the name of the provider
     * @return
     */
    abstract String getProviderName()

    /**
     * Defines the upload for the submission
     * The possible upload types are already defined in the repository object of the repository provider
     * @param type
     */
    abstract void setUploadType(String type)

    /**
     * Creates a SubmissionManager through which the submission process can be managed
     * @return SubmissinManager
     */
    abstract SubmissionManager create()

    /**
     * Returns the repository object describing the provider
     * @return
     */
    abstract Repository getRepositoryDescription()
}
