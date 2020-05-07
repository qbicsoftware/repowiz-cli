package life.qbic.repowiz.spi

import life.qbic.repowiz.model.SubmissionModel


interface SubmissionManager {
    List<String> validateSubmissionModel(SubmissionModel model)
    SubmissionModel getProviderSubmissionModel()
    //service for retrieving the submission model = quote manager interface in baeldung example
    void downloadSubmission(String filename)
}