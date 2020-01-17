package life.qbic.repowiz.prepare

import life.qbic.repowiz.RepositoryUploadService
import spock.lang.Specification

class PrepareSubmissionSpecification extends Specification{

    def mockedMappedMetadata = Mock(MappedMetadata)
    def mockedOutput = Mock(ProjectSubmissionOutput)
    def mockedUploadService = Mock(RepositoryUploadService)

    def prepareSubmission = new PrepareSubmissionImpl(mockedMappedMetadata,mockedOutput,mockedUploadService)

    def ""(){

    }
}
