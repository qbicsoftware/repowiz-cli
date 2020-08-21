package life.qbic.repowiz.prepare.projectSearch

import life.qbic.repowiz.model.RepoWizProject
import life.qbic.repowiz.model.RepoWizSample

/**
 * Interface to define the output of the project search is handled
 *
 * This interface should be used whenever the results of a project search need to be obtained
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface ProjectSearchOutput {

    /**
     * Method that creates a RepoWiz SubmissionModel from the a RepoWizProject and
     * RepoWizSamples
     *
     * @param project
     * @param samples
     * @return
     */
    def createSubmissionModel(RepoWizProject project, List<RepoWizSample> samples)

    /**
     * Method to forward information that shall be displayed to the user
     * @param message
     * @return
     */
    def userNotification(String message)

}