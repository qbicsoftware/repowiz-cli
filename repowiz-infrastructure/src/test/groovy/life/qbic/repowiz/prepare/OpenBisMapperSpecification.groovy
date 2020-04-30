package life.qbic.repowiz.prepare

import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.prepare.projectSearch.OpenBisMapper
import life.qbic.xml.properties.Property
import life.qbic.xml.properties.PropertyType
import spock.lang.Specification

class OpenBisMapperSpecification extends Specification{


    OpenBisMapper openBisMapper

    def setup(){
        String path = "metadataMapping/openbisMapping.json"
        InputStream stream =  OpenBisMapper.class.getClassLoader().getResourceAsStream(path)
        JsonParser jsonParser = new JsonParser(stream)
        Map repoWizTerms = jsonParser.parse()

        openBisMapper = new OpenBisMapper(repoWizTerms)
    }

    def "OpenBis properties are properly mapped"(){
        when:
        def res = openBisMapper.mapProperties(["Q_PROJECT_DETAILS":"text"])

        then:
        res == ["design":"text"]
    }

    def "OpenBis properties with no mapping are ignored"(){
        when:
        def res = openBisMapper.mapProperties(["Q_PROJECT_DETAILS":"text", "this is a test":"do not map the label of this value"])

        then:
        res == ["design":"text"]
    }

    def "Convert Sequencer Device correctly"(){
        when:
        def res = openBisMapper.mapProperties(["Q_SEQUENCER_DEVICE":"Illumina HiSeq 2500 at IMGAG", "this is a test":"do not map the label of this value"])

        then:
        res == ["instrument model":"Illumina HiSeq 2500"]
    }

    def "DataSet files are mapped as list"(){
        when:
        def res = openBisMapper.mapFiles(["file1.fasta", "file2.fasta", "file3.fasta"],"Q_NGS_RAW_DATA")

        then:
        res.sort() == ["raw file":["file1.fasta","file2.fasta","file3.fasta"]].sort()
    }

    def "Conditions are translated properly"(){
        given:
        Property prop1 = new Property("genotype","mutant", PropertyType.Factor)
        Property prop2 = new Property("healthy","sick", PropertyType.Factor)

        List<Property> properties = [prop1, prop2]

        when:
        def res = openBisMapper.mapConditions(properties)

        then:
        res.sort() == ["characteristic genotype" : "mutant","characteristic healthy": "sick"].sort()
    }

    def "duplicate properties are masked properly"(){
    when:
        def res = openBisMapper.maskDuplicateProperties("Q_BIOLOGICAL_SAMPLE",["Q_SECONDARY_NAME":"this is secondary name"])

        then:
        res == ["Q_SECONDARY_NAME_Q_BIOLOGICAL_SAMPLE":"this is secondary name"]

    }
}
