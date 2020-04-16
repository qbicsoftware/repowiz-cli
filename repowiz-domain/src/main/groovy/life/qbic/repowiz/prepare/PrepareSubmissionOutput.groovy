package life.qbic.repowiz.prepare

import life.qbic.repowiz.Repository
import life.qbic.repowiz.model.SubmissionModel

interface PrepareSubmissionOutput {

    def finaliseSubmission(SubmissionModel submission, Repository repository)
    def transferQuestion(List<String> question)
    def displayAnswer(String answer)

}