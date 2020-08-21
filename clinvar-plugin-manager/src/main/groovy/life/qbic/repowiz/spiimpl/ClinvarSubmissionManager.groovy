package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.model.SubmissionModel
import life.qbic.repowiz.spi.SubmissionManager

class ClinvarSubmissionManager implements SubmissionManager {

    @Override
    List validateSubmissionModel(SubmissionModel model) {
        return null
    }

    @Override
    SubmissionModel getProviderSubmissionModel() {
        return null
    }

    @Override
    void downloadSubmission(String fileName) {

    }
}
