package life.qbic.repowiz.prepare.projectSearch


import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface ProjectSearchOutput {

    def transferProjectMetadata(RepoWizProject project)
    //def transferSampleMetadata(List<RepoWizSample> meta)
    def userNotification(String message)
}