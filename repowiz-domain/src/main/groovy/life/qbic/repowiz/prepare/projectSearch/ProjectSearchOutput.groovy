package life.qbic.repowiz.prepare.projectSearch


import life.qbic.repowiz.prepare.model.RepoWizProject

interface ProjectSearchOutput {

    def addRequiredFields(RepoWizProject project)
    def userNotification(String message)
}