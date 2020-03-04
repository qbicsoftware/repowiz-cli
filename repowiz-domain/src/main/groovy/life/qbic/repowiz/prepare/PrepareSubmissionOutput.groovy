package life.qbic.repowiz.prepare

import life.qbic.repowiz.prepare.model.RepoWizProject

interface PrepareSubmissionOutput {

    def validateProject(RepoWizProject project)
    def transferQuestion(List<String> question)
    def displayAnswer(String answer)

}