package life.qbic.repowiz.submit

interface FinaliseSubmission {

    def checkSubmissionValidity(HashMap<String,String> metadata)
    def createSubmissionOverview(HashMap<String,String> metadata,List<String> files)
    def submitData()
}