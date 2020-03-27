package life.qbic.repowiz.finalise.api

import life.qbic.repowiz.model.SubmissionModel


interface SubmissionManager {
    List validateSubmissionModel(SubmissionModel model)
    String getSubmissionSummary()
    //service for retrieving the submission model = quote manager interface in baeldung example
    void downloadSubmission()
}