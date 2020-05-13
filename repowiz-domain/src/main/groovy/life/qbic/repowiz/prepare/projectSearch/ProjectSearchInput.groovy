package life.qbic.repowiz.prepare.projectSearch

/**
 * Interface to define the input of the ProjectSearch class
 */
interface ProjectSearchInput {

    /**
     * Triggers the class to load the project based on the given projectid
     * @param projectID
     * @return
     */
    def loadProjectInformation(String projectID)

}
