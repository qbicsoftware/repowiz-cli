package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

class GeoTargetRepositoryProvider extends TargetRepositoryProvider{

    String providerName = "Geo"
    String uploadType

    @Override
    void setUploadType(String uploadType) {
        this.uploadType = uploadType
    }

    @Override
    SubmissionManager create() {
        GeoSubmissionManager manager = new GeoSubmissionManager()
        if(uploadType != null) manager.createGeoSubmissionObject(uploadType)
        return manager
    }
}
