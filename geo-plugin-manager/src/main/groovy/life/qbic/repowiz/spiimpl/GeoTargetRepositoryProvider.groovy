package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

class GeoTargetRepositoryProvider extends TargetRepositoryProvider{

    String providerName = "geo"

    @Override
    SubmissionManager create() {
        return new GeoSubmissionManager()
    }
}
