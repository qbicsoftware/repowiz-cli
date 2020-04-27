import life.qbic.repowiz.submissionTypes.GeoHtsSubmission
import spock.lang.Specification

class GeoTemplateParserSpecification extends Specification{

    def "writes workbook to file successfully"(){
        given:
        GeoHtsSubmission submission = new GeoHtsSubmission()
        HashMap values = ["series_title":"this is a title","data processing pipeline_data processing step":"blabljabljabkjbljlbajslbj"]

        when:
        submission.writeToWorkbook("thisIsFileName",values)
        //todo delete file from test

        then:
        true
    }
}
