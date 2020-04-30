import life.qbic.repowiz.model.GeoSample
import life.qbic.repowiz.submissionTypes.GeoHtsSubmission
import spock.lang.Specification

class GeoTemplateParserSpecification extends Specification{

    def "writes workbook to file successfully"(){
        given:
        GeoHtsSubmission submission = new GeoHtsSubmission()

        HashMap<String,String> values = ["series_title":"this is a title","data processing pipeline_data processing step":"blabla"]
        HashMap properties = ["samples_Sample name":"sample 1","samples_title": "blablbl", "samples_source name":"Human",
                          "protocols_library strategy":"test", "data processing pipeline_data processing step":"blabla",
                          "raw files_instrument model":"illumina"]
        GeoSample sample = new GeoSample("name",properties,["file1.fastq","file2.fastq","file3.fastq"])


        when:
        submission.writeProjectToWorkbook(values)
        submission.writeSampleToWorkbook([sample,sample,sample])
        submission.downloadFile("fileName")

        then:
        true
    }
}
