package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

class GeoTargetRepositoryProvider extends TargetRepositoryProvider{

    String providerName = "life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider"

    @Override
    SubmissionManager create() {
        return new GeoSubmissionManager()
    }
}
