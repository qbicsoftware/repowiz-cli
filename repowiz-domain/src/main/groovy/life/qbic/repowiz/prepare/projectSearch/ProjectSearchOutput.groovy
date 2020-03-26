package life.qbic.repowiz.prepare.projectSearch

import life.qbic.repowiz.model.RepoWizProject
import life.qbic.repowiz.model.RepoWizSample


interface ProjectSearchOutput {

    def transferProjectData(RepoWizProject project, List<RepoWizSample> samples)
    def userNotification(String message)

}