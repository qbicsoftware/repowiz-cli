package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.spi.SubmissionManager
import life.qbic.repowiz.spi.TargetRepository
import life.qbic.repowiz.spi.TargetRepositoryProvider

class GeoTargetRepositoryProvider extends TargetRepositoryProvider {

    String providerName = "Geo"
    String uploadType

    @Override
    void setUploadType(String uploadType) {
        this.uploadType = uploadType
    }

    @Override
    SubmissionManager create() {
        GeoSubmissionManager manager = new GeoSubmissionManager()
        if (uploadType != null) manager.createGeoSubmissionObject(uploadType)
        return manager
    }

    @Override
    Repository getRepositoryDescription() {
        RepositoryDatabaseConnector connector = new RepositoryDatabaseConnector(new TargetRepository())
        InputStream stream = GeoTargetRepositoryProvider.class.getClassLoader().getResourceAsStream("geo.json")

        return connector.parseRepo(stream)
    }


}
