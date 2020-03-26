package life.qbic.repowiz.finalise.api


import life.qbic.repowiz.prepare.model.SubmissionModel

interface SubmissionManager {
    SubmissionModel validateSubmissionModel(SubmissionModel model)
    //service for retrieving the submission model = quote manager interface in baeldung example
    void createSubmission()
}