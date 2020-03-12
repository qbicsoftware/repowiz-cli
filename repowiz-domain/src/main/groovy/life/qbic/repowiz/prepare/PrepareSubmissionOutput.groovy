package life.qbic.repowiz.prepare

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface PrepareSubmissionOutput {

    def validateProject(RepoWizProject project, List<RepoWizSample> samples)
    def submissionDetails(String repoName, String uploadType)
    def transferQuestion(List<String> question)
    def displayAnswer(String answer)

}