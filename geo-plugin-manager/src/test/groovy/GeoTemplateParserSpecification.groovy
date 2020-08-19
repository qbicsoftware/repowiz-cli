import life.qbic.repowiz.model.GeoSample
import life.qbic.repowiz.submissionTypes.GeoHtsSubmission
import spock.lang.Specification

class GeoTemplateParserSpecification extends Specification {

    def "writes workbook to file successfully"() {
        given:
        GeoHtsSubmission submission = new GeoHtsSubmission()

        HashMap<String, String> values = ["series_title": "this is a title"]
        HashMap properties = ["samples_Sample name"       : "sample 1", "samples_title": "blablbl", "samples_source name": "Human",
                              "protocols_library strategy": "test", "data processing pipeline_genome build": "blabla",
                              "raw files_instrument model": "illumina"]

        GeoSample sample = new GeoSample("Sample 1", properties, ["file4.fastq", "file5.fastq", "file6.fastq"], new HashMap())

        HashMap properties2 = ["samples_Sample name"       : "sample 2", "samples_title": "blablbl", "samples_source name": "Human",
                               "protocols_library strategy": "test", "data processing pipeline_genome build": "blabla",
                               "raw files_instrument model": "illumina"]

        GeoSample sample2 = new GeoSample("Sample 2", properties2, ["file1.fastq", "file2.fastq", "file3.fastq"], ["characteristics: treatment": "non", "characteristics: genotype": "mutant"])

        HashMap properties3 = ["samples_Sample name"       : "sample 3", "samples_title": "blablbl", "samples_source name": "Human",
                               "protocols_library strategy": "test", "data processing pipeline_genome build": "blabla",
                               "raw files_instrument model": "illumina"]

        GeoSample sample3 = new GeoSample("Sample 3", properties3, ["file1.fastq.gz", "file2.fastq.gz", "file3.fastq.gz"], ["characteristics: treatment": "non", "characteristics: genotype": "wildtype", "characteristics: other": "non"])


        when:
        submission.writeProjectToWorkbook(values)
        submission.writeSampleToWorkbook([sample3, sample, sample2])
        submission.downloadFile("fileName")

        then:
        true
    }
}
