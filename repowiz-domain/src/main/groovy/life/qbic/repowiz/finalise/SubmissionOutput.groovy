package life.qbic.repowiz.finalise

/**
 * Information about the submission that is accessible from outside the {@link FinaliseSubmission} use case
 *
 * This interface should be implemented whenever information needs to be injected into {@link FinaliseSubmission}
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface SubmissionOutput {

    /**
     * Information that should be displayed to the user
     * @param information
     */
    void displayUserInformation(String information)

    /**
     * Information for the user in form of a list
     * @param text
     */
    void displayUserInformation(List<String> text)

    /**
     * Summary of the project with the properties of the provider
     * that shall be displayed to the user
     * @param project
     * @param id
     */
    void displayProjectSummary(Map project, String id)

    /**
     * Summary of each samples with the properties for the provider
     * that shall be displayed to the user
     * @param samples
     */
    void displaySampleSummary(Map<String, Map> samples)

    /**
     * Answer Possibilities based on which the user can verify his data
     * @param choices
     */
    void verifySubmission(List<String> choices)

}