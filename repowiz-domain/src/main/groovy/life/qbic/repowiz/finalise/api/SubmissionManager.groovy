package life.qbic.repowiz.finalise.api

import life.qbic.repowiz.model.SubmissionModel


interface SubmissionManager {
    List<String> validateSubmissionModel(SubmissionModel model)
    List<String> getSubmissionSummary()
    //service for retrieving the submission model = quote manager interface in baeldung example
    void downloadSubmission(String filename)
}