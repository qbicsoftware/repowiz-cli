package life.qbic.repowiz.finalise

interface SubmissionOutput {

    def displaySubmissionSummary(String summary)
    def displayStepsAfterSubmission(String text)
    def verifySubmission()

}