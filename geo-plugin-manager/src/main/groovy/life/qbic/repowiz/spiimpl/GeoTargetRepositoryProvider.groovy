package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider

class GeoTargetRepositoryProvider implements TargetRepositoryProvider{

    @Override
    SubmissionManager create() {
        return new GeoSubmissionManager()
    }
}
