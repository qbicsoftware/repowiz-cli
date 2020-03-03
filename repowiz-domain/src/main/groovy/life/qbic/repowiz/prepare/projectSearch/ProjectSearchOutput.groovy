package life.qbic.repowiz.prepare.projectSearch


import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface ProjectSearchOutput {

    def transferProjectMetadata(List<RepoWizProject> meta)
    def transferSampleMetadata(List<RepoWizSample> meta)
    def userNotification(String message)
}