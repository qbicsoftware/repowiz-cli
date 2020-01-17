package life.qbic.repowiz.submit

import life.qbic.repowiz.RepositoryUploadService

class FinaliseSubmissionImpl implements VerifySubmission, FinaliseSubmission{

    SubmissionOutput output

    FinaliseSubmissionImpl(SubmissionOutput out){
        output = out
    }

    @Override
    def checkSubmissionValidity(HashMap<String,String> metadata) {
        return null
    }

    @Override
    def createSubmissionOverview(HashMap<String, String> metadata, List<String> files) {
        return null
    }

    @Override
    def submitData() {
        return null
    }

    @Override
    def verifyCorrectnessOfSubmission(boolean correct) {
        return null
    }
}