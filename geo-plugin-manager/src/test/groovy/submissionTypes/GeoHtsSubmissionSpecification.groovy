package submissionTypes

import life.qbic.repowiz.model.GeoSample
import life.qbic.repowiz.submissionTypes.GeoHtsSubmission
import spock.lang.Specification

class GeoHtsSubmissionSpecification extends Specification {

    GeoHtsSubmission submission

    def setup() {
        submission = new GeoHtsSubmission()
    }

    def "detect all missing fields"() {
        given:
        Map defined = ["series_title": "this is a title"]
        when:
        def res = submission.determineMissingFields(defined)
        then:
        res.size() == 25
    }

    def "get list of all characteristics for all samples"() {
        given:
        GeoHtsSubmission submission = new GeoHtsSubmission()

        HashMap properties = ["samples_Sample name"       : "sample 1", "samples_title": "blablbl", "samples_source name": "Human",
                              "protocols_library strategy": "test", "data processing pipeline_genome build": "blabla",
                              "raw files_instrument model": "illumina"]

        GeoSample sample = new GeoSample("name1", properties, ["file1.fastq", "file2.fastq", "file3.fastq", "file4.fastq"], ["characteristic: treatment": "non", "characteristic: genotype": "mutant"])

        HashMap properties2 = ["samples_Sample name"       : "sample 2", "samples_title": "blablbl", "samples_source name": "Human",
                               "protocols_library strategy": "test", "data processing pipeline_genome build": "blabla",
                               "raw files_instrument model": "illumina"]

        GeoSample sample2 = new GeoSample("name2", properties2, ["file1.fastq", "file2.fastq", "file3.fastq"], ["characteristic: treatment": "non", "characteristic: genotype": "wildtype", "characteristic: other": "non"])


        when:
        List res = submission.getAllCharacteristicsTags([sample, sample2])

        then:
        res.sort() == ["characteristic: treatment", "characteristic: genotype", "characteristic: other"].sort()
    }

    def "get max num of files for all samples"() {
        given:
        GeoHtsSubmission submission = new GeoHtsSubmission()

        HashMap properties = ["samples_Sample name"       : "sample 1", "samples_title": "blablbl", "samples_source name": "Human",
                              "protocols_library strategy": "test", "data processing pipeline_genome build": "blabla",
                              "raw files_instrument model": "illumina"]

        GeoSample sample = new GeoSample("name1", properties, ["file1.fastq", "file2.fastq", "file3.fastq", "file4.fastq"], ["characteristic: treatment": "non", "characteristic: genotype": "mutant"])

        HashMap properties2 = ["samples_Sample name"       : "sample 2", "samples_title": "blablbl", "samples_source name": "Human",
                               "protocols_library strategy": "test", "data processing pipeline_genome build": "blabla",
                               "raw files_instrument model": "illumina"]

        GeoSample sample2 = new GeoSample("name2", properties2, ["file1.fastq", "file2.fastq", "file3.fastq"], ["characteristic: treatment": "non", "characteristic: genotype": "wildtype", "characteristic: other": "non"])


        when:
        int res = submission.maxNumFiles([sample, sample2])

        then:
        res == 4
    }


    // Sample 6:[protocols_library construction protocol:TruSeq Stranded mRNA, samples_source name:Cell Line, samples_molecule:RNA, data processing pipeline_genome build:Genome: hg19, description:9.5, samples_organism:Homo sapiens, samples_raw file:[I16R019b01_01_S4_L001_R1_001.fastq.gz, I16R019b01_01_S4_L002_R1_001.fastq.gz, I16R019b01_01_S4_L003_R1_001.fastq.gz, I16R019b01_01_S4_L004_R1_001.fastq.gz], raw files_instrument model:Illumina HiSeq 2500, samples_characteristics: genotype:wildtype]]

    //[Sample 1:[integrity number:9.1, raw file:[I16R019a02_01_S3_L001_R1_001.fastq.gz, I16R019a02_01_S3_L002_R1_001.fastq.gz,
    // I16R019a02_01_S3_L003_R1_001.fastq.gz, I16R019a02_01_S3_L004_R1_001.fastq.gz], library protocol:TruSeq Stranded mRNA,
    // organism:Homo sapiens, instrument model:Illumina HiSeq 2500, source name:Cell Line, characteristic genotype:mutant, molecule:RNA,
    // sequencing mode:PAIRED_END, ref genome:Genome: hg19],
    // Sample 5:[integrity number:8.3, raw file:[I16R019a01_01_S2_L001_R1_001.fastq.gz, I16R019a01_01_S2_L002_R1_001.fastq.gz,
    // I16R019a01_01_S2_L003_R1_001.fastq.gz, I16R019a01_01_S2_L004_R1_001.fastq.gz],
    // library protocol:TruSeq Stranded mRNA, organism:Homo sapiens, instrument model:Illumina HiSeq 2500,
    // source name:Cell Line, characteristic genotype:wildtype, molecule:RNA, sequencing mode:PAIRED_END, ref genome:Genome: hg19],
    // Sample 4:[integrity number:9.5, raw file:[I16R019d02_01_S9_L001_R1_001.fastq.gz, I16R019d02_01_S9_L002_R1_001.fastq.gz,
    // I16R019d02_01_S9_L003_R1_001.fastq.gz, I16R019d02_01_S9_L004_R1_001.fastq.gz], library protocol:TruSeq Stranded mRNA,
    // organism:Homo sapiens, instrument model:Illumina HiSeq 2500, source name:Cell Line, characteristic genotype:mutant,
    // molecule:RNA, sequencing mode:PAIRED_END, ref genome:Genome: hg19],
    // Sample 3:[integrity number:10.0, raw file:[I16R019c02_01_S7_L001_R1_001.fastq.gz, I16R019c02_01_S7_L002_R1_001.fastq.gz,
    // I16R019c02_01_S7_L003_R1_001.fastq.gz, I16R019c02_01_S7_L004_R1_001.fastq.gz], library protocol:TruSeq Stranded mRNA,
    // organism:Homo sapiens, instrument model:Illumina HiSeq 2500, source name:Cell Line, characteristic genotype:mutant,
    // molecule:RNA, sequencing mode:PAIRED_END, ref genome:Genome: hg19],
    // Sample 2:[integrity number:6.4, raw file:[I16R019b02_01_S5_L001_R1_001.fastq.gz, I16R019b02_01_S5_L002_R1_001.fastq.gz,
    // I16R019b02_01_S5_L003_R1_001.fastq.gz, I16R019b02_01_S5_L004_R1_001.fastq.gz], library protocol:TruSeq Stranded mRNA,
    // organism:Homo sapiens, instrument model:Illumina HiSeq 2500, source name:Cell Line, characteristic genotype:mutant,
    // molecule:RNA, sequencing mode:PAIRED_END, ref genome:Genome: hg19],
    // Sample 8:[integrity number:10.0, organism:Homo sapiens, source name:Cell Line, characteristic genotype:wildtype, molecule:RNA],
    // Sample 7:[integrity number:7.5, raw file:[I16R019d01_01_S8_L001_R1_001.fastq.gz, I16R019d01_01_S8_L002_R1_001.fastq.gz,
    // I16R019d01_01_S8_L003_R1_001.fastq.gz, I16R019d01_01_S8_L004_R1_001.fastq.gz], library protocol:TruSeq Stranded mRNA,
    // organism:Homo sapiens, instrument model:Illumina HiSeq 2500, source name:Cell Line, characteristic genotype:wildtype,
    // molecule:RNA, sequencing mode:PAIRED_END, ref genome:Genome: hg19],
    // Sample 6:[integrity number:9.5, raw file:[I16R019b01_01_S4_L001_R1_001.fastq.gz, I16R019b01_01_S4_L002_R1_001.fastq.gz,
    // I16R019b01_01_S4_L003_R1_001.fastq.gz, I16R019b01_01_S4_L004_R1_001.fastq.gz], library protocol:TruSeq Stranded mRNA,
    // organism:Homo sapiens, instrument model:Illumina HiSeq 2500, source name:Cell Line, characteristic genotype:wildtype,
    // molecule:RNA, sequencing mode:PAIRED_END, ref genome:Genome: hg19]]
}
