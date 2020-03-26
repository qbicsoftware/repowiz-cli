package life.qbic.repowiz.finalise

interface SubmissionOutput {

    def displaySubmissionSummary(String summary)
    def displayStepsAfterSubmission(List<String> text)
    def verifySubmission()

}