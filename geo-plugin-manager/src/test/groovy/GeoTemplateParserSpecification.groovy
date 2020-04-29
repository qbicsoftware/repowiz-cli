
import life.qbic.repowiz.submissionTypes.GeoHtsSubmission
import spock.lang.Specification

class GeoTemplateParserSpecification extends Specification{

    def "writes workbook to file successfully"(){
        given:
        GeoHtsSubmission submission = new GeoHtsSubmission()

        HashMap<String,String> values = ["series_title":"this is a title","data processing pipeline_data processing step":"blabla"]
        HashMap sample = ["samples_Sample name":"sample 1","samples_title": "blablbl", "samples_source name":"Human",
                          "protocols_library strategy":"test", "data processing pipeline_data processing step":"blabla",
                          "raw files_instrument model":"illumina"]


        when:
        submission.writeToWorkbook(values, [sample,sample,sample])
        submission.downloadFile("fileName")

        then:
        true
    }
}
