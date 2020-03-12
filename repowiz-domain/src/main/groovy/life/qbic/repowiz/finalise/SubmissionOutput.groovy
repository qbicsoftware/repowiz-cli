package life.qbic.repowiz.finalise

interface SubmissionOutput {

    def submissionSummary(String summary)
    List<String> subsequentSteps()
    String submissionIdentifier()

}