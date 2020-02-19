package life.qbic.repowiz.prepare.projectSearch

import life.qbic.repowiz.prepare.model.RepoWizData
import life.qbic.repowiz.prepare.model.RepoWizExperiment
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample


interface ProjectSearchService {

    List<RepoWizProject> getProjectMetadata(String projectID)
    List<RepoWizExperiment>getExperimentMetadata(String experimentID)
    List<RepoWizSample> getSampleMetadata(String projectID)
    List<RepoWizData> getFilesForSamples(String sampleID)

}
