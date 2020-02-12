package life.qbic.repowiz.prepare


import spock.lang.Specification

class PrepareSubmissionSpecification extends Specification{

    def mockedMappedMetadata = Mock(MappedMetadata)
    def mockedOutput = Mock(PrepareSubmissionOutput)
    def details = Mock(ProjectSearchService)

    def prepareSubmission = new PrepareSubmissionImpl(mockedMappedMetadata,mockedOutput,details)

    /**def "Each project sample needs an output file"(){

    }*/


}
