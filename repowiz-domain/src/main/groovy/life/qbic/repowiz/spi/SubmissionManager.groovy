package life.qbic.repowiz.spi

import life.qbic.repowiz.model.SubmissionModel

/**
 *  Interface for managing the submission within the target repository provider
 */
interface SubmissionManager {

    /**
     * Validates the submission model through checking if all required information is provided,
     * all missing fields are returned
     *
     * @param model
     * @return
     */
    List<String> validateSubmissionModel(SubmissionModel model)

    /**
     * Returns the submission model which contains the metadata terms of the provider
     * @return
     */
    SubmissionModel getProviderSubmissionModel()

    /**
     * Triggers the download of the submission in the provider implementation
     * @param filename
     */
    void downloadSubmission(String filename)
}