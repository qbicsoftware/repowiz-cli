package submissionTypes

import life.qbic.repowiz.submissionTypes.GeoHtsSubmission
import spock.lang.Specification

class GeoHtsSubmissionSpecification extends Specification{

    GeoHtsSubmission submission

    def setup(){
        submission = new GeoHtsSubmission()
    }

    def "detect all missing fields"(){
        given:
        Map defined = ["series_title":"this is a title"]
        when:
        def res = submission.determineMissingFields(defined)
        then:
        res.size() == 25
    }
}
