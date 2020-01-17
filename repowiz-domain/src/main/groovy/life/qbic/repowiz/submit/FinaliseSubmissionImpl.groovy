package life.qbic.repowiz.submit

import life.qbic.repowiz.RepositoryUploadService

class FinaliseSubmissionImpl implements VerifySubmission, FinaliseSubmission{

    SubmissionOutput output

    FinaliseSubmissionImpl(SubmissionOutput out){
        output = out
    }

    @Override
    def checkSubmissionValidity(Object metadata) {
        return null
    }

    @Override
    def createSubmissionOverview() {
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