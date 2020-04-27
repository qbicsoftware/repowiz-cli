package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.model.SubmissionModel

class ClinvarSubmissionManager implements SubmissionManager{

    @Override
    List validateSubmissionModel(SubmissionModel model) {
        return null
    }

    @Override
    String getSubmissionSummary() {
        return null
    }

    @Override
    void downloadSubmission(String fileName) {

    }
}
