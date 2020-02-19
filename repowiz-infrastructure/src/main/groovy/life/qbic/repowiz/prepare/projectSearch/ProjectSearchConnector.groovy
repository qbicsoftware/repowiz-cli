package life.qbic.repowiz.prepare.projectSearch

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.Project
import life.qbic.repowiz.prepare.model.RepoWizData
import life.qbic.repowiz.prepare.model.RepoWizExperiment
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

class ProjectSearchConnector implements ProjectSearchService{

    Project project
    IApplicationServerApi v3
    String sessionToken

    ProjectSearchConnector(IApplicationServerApi v3, String session){
        this.v3 = v3
        sessionToken = session
    }


    @Override
    List<RepoWizProject> getProjectMetadata(String projectID) {
        return null
    }

    @Override
    List<RepoWizExperiment> getExperimentMetadata(String experimentID) {
        return null
    }

    @Override
    List<RepoWizSample> getSampleMetadata(String projectID) {
        return null
    }

    @Override
    List<RepoWizData> getFilesForSamples(String sampleID) {
        return null
    }
}
