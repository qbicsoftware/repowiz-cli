package life.qbic.repowiz.spi

import life.qbic.repowiz.Repository

/**
 * SPI interface that determines the functions a repository provider must have
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