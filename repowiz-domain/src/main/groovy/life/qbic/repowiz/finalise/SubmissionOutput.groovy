package life.qbic.repowiz.finalise

interface SubmissionOutput {

    def displayUserInformation(String information)
    def displayUserInformation(List<String> text)
    def verifySubmission(List<String> text)

}