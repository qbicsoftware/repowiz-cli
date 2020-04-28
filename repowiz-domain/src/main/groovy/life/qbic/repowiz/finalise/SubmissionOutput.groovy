package life.qbic.repowiz.finalise


interface SubmissionOutput {

    def displayUserInformation(String information)
    def displayUserInformation(List<String> text)
    def displayProjectSummary(HashMap project, String id)
    def displaySampleSummary(HashMap<String, HashMap> samples)
    def verifySubmission(List<String> text)

}