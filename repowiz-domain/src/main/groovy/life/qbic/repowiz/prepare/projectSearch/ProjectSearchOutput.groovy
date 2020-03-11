package life.qbic.repowiz.prepare


import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface ProjectSearchOutput {

    def transferProjectData(RepoWizProject project, List<RepoWizSample> samples)
    def userNotification(String message)
}