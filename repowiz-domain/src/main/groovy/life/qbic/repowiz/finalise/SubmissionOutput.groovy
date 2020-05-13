package life.qbic.repowiz.finalise

/**
 * Information about the submission that is accessible from outside the FinaliseSubmission use case
 */
interface SubmissionOutput {

    /**
     * Information that should be displayed to the user
     * @param information
     * @return
     */
    def displayUserInformation(String information)

    /**
     * Information for the user in form of a list
     * @param text
     * @return
     */
    def displayUserInformation(List<String> text)

    /**
     * Summary of the project with the properties of the provider
     * that shall be displayed to the user
     * @param project
     * @param id
     * @return
     */
    def displayProjectSummary(HashMap project, String id)

    /**
     * Summary of each samples with the properties for the provider
     * that shall be displayed to the user
     * @param samples
     * @return
     */
    def displaySampleSummary(HashMap<String, HashMap> samples)

    /**
     * Answer Possibilities based on which the user can verify his data
     * @param choices
     * @return
     */
    def verifySubmission(List<String> choices)

}