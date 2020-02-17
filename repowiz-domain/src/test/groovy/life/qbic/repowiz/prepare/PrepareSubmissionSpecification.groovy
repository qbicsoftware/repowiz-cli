package life.qbic.repowiz.prepare

import life.qbic.repowiz.prepare.mapping.MapInfoInput
import spock.lang.Specification

class PrepareSubmissionSpecification extends Specification{

    def mockedMappedMetadata = Mock(MapInfoInput)
    def mockedOutput = Mock(PrepareSubmissionOutput)
    def details = Mock(ProjectSearchService)

    def prepareSubmission = new PrepareSubmissionImpl(mockedMappedMetadata,mockedOutput,details)

    /**def "Each project sample needs an output file"(){

    }*/


}
