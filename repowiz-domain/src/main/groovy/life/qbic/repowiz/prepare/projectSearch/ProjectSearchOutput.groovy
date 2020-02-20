package life.qbic.repowiz.prepare.projectSearch

import life.qbic.repowiz.prepare.model.RepoWizData
import life.qbic.repowiz.prepare.model.RepoWizExperiment
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface ProjectSearchOutput {

    def transferProjectMetadata(List<RepoWizProject> meta)
    def transferExperimentMetadata(List<RepoWizExperiment> meta)
    def transferSampleMetadata(List<RepoWizSample> meta)
    def transferDataForSamples(List<RepoWizData> meta)
    def userNotification(String message)
}