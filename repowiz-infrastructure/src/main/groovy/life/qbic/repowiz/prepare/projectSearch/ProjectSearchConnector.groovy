package life.qbic.repowiz.prepare.projectSearch

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.asapi.v3.dto.common.search.SearchResult
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.Project
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.fetchoptions.ProjectFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.search.ProjectSearchCriteria
import life.qbic.repowiz.prepare.model.RepoWizData
import life.qbic.repowiz.prepare.model.RepoWizExperiment
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

class ProjectSearchConnector implements ProjectSearchInput{

    Project project
    //safe her project info/repowiz data types?
    //need mapper?

    IApplicationServerApi v3
    String sessionToken


    ProjectSearchConnector(IApplicationServerApi v3, String session){
        this.v3 = v3
        sessionToken = session

    }

    @Override
    def loadProject(String projectID){
        // invoke other API methods using the session token, for instance search for spaces
        ProjectSearchCriteria projectSearchCriteria = new ProjectSearchCriteria()
        projectSearchCriteria.withCode().thatEquals(projectID)

        ProjectFetchOptions projectFetchOptions = new ProjectFetchOptions()
        projectFetchOptions.withSpace()

        SearchResult<Project> projects = v3.searchProjects(sessionToken, projectSearchCriteria, projectFetchOptions);
        project = projects.getObjects().get(0)

        checkSpaceAvailability(projectID)
    }

    private void checkSpaceAvailability(String projectCode) {

        if (project == null) {
            System.out.println("Project " + projectCode + " does not exist for user")
            v3.logout(sessionToken)
            System.exit(0)
        } else {
            System.out.println("Found project " + projectCode + " for user")

        }
    }
}
