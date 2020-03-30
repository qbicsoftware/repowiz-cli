package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

class ClinvarTargetRepositoryProvider extends TargetRepositoryProvider{

    String providerName = "life.qbic.repowiz.spiimpl.ClinvarTargetRepositoryProvider"
    String uploadType

    @Override
    void setUploadType(String type) {
        this.uploadType = type
    }

    @Override
    SubmissionManager create() {
        return new ClinvarSubmissionManager()
    }
}
