package life.qbic.repowiz.prepare.projectSearch

/**
 * Interface to define the input of a project search
 *
 * This interface should be used whenever a project search is defined
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface ProjectSearchInput {

    /**
     * Triggers the class to load the project based on the given projectid
     * @param projectID
     * @return
     */
    def loadProjectInformation(String projectID)

}
