package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.spi.SubmissionManager
import life.qbic.repowiz.spi.TargetRepository
import life.qbic.repowiz.spi.TargetRepositoryProvider

class ClinvarTargetRepositoryProvider extends TargetRepositoryProvider{

    String providerName = "Clinvar"
    String uploadType

    @Override
    void setUploadType(String type) {
        this.uploadType = type
    }

    @Override
    SubmissionManager create() {
        return new ClinvarSubmissionManager()
    }

    @Override
    Repository getRepositoryDescription() {
        RepositoryDatabaseConnector connector = new RepositoryDatabaseConnector(new TargetRepository())
        InputStream stream = ClinvarTargetRepositoryProvider.class.getClassLoader().getResourceAsStream("clinvar.json")

        return connector.parseRepo(stream)
    }
}
