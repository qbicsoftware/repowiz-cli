package life.qbic.repowiz.prepare

import life.qbic.repowiz.prepare.openBis.OpenBisMapper
import life.qbic.xml.properties.Property
import life.qbic.xml.properties.PropertyType
import spock.lang.Specification

class OpenBisMapperSpecification extends Specification{

    OpenBisMapper mapp = new OpenBisMapper()


    def "OpenBis properties are properly mapped"(){
        when:
        def res = mapp.mapProperties(["Q_PROJECT_DETAILS":"text"])

        then:
        res == ["series_design":"text"]
    }

    def "OpenBis properties with no mapping are ignored"(){
        when:
        def res = mapp.mapProperties(["Q_PROJECT_DETAILS":"text", "this is a test":"do not map the label of this value"])

        then:
        res == ["series_design":"text"]
    }

    def "DataSet files are mapped as list"(){
        when:
        def res = mapp.mapFiles(["file1.fasta", "file2.fasta", "file3.fasta"],"Q_NGS_RAW_DATA")

        then:
        res.sort() == ["samples_raw file":["file1.fasta","file2.fasta","file3.fasta"]].sort()
    }

    def "Conditions are translated properly"(){
        given:
        Property prop1 = new Property("genotype","mutant", PropertyType.Factor)
        Property prop2 = new Property("healthy","sick", PropertyType.Factor)

        List<Property> properties = [prop1, prop2]

        when:
        def res = mapp.mapConditions(properties)

        then:
        res.sort() == ["samples_characteristics genotype" : "mutant","samples_characteristics healthy": "sick"].sort()
    }

    def "duplicate properties are masked properly"(){
    when:
        def res = mapp.maskDuplicateProperties("Q_BIOLOGICAL_SAMPLE",["Q_SECONDARY_NAME":"this is secondary name"])

        then:
        res == ["Q_SECONDARY_NAME_Q_BIOLOGICAL_SAMPLE":"this is secondary name"]

    }
}
