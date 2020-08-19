package life.qbic.repowiz.model

/**
 * This class serves as DTO to represent scientific projects
 *
 * This class should be used whenever a project needs used or handled in RepoWiz
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class RepoWizProject {

    final String projectID
    final HashMap<String, String> projectProperties

    /**
     * Creates a RepoWizProject based on the project code and the metadata
     * @param project defined by a code
     * @param meta describing the metadata of the project
     */
    RepoWizProject(String project, HashMap meta) {
        projectID = project
        projectProperties = meta
    }

    //todo do not implement any logic in here!!!!
}
