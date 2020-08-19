package life.qbic.repowiz.prepare

import life.qbic.repowiz.Repository
import life.qbic.repowiz.model.SubmissionModel

/**
 * Defines what information prepared in {@link PrepareSubmissionImpl} can be obtained outside the class
 *
 * This class should be used whenever information about the submission need to be accessed
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface PrepareSubmissionOutput {

    /**
     * Forwards the data form the DMS in form of the submission model and the goal repository description
     * in order to finalise the submission based on this information
     *
     * @param submission
     * @param repository
     * @return
     */
    def finaliseSubmission(SubmissionModel submission, Repository repository)

    /**
     * Transfers userquestions that need to be displayed to the user
     * @param question
     * @return
     */
    def transferQuestion(List<String> question)

    /**
     * Transfers the user answer so that it can be displayed to the user
     * @param answer
     * @return
     */
    def displayAnswer(String answer)

}