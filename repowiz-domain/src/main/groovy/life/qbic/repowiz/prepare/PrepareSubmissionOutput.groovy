package life.qbic.repowiz.prepare

import life.qbic.repowiz.Repository
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import life.qbic.repowiz.prepare.model.SubmissionModel

interface PrepareSubmissionOutput {

    def validateProject(SubmissionModel submission)
    def submissionDetails(Repository repository)
    def transferQuestion(List<String> question)
    def displayAnswer(String answer)

}