package life.qbic.repowiz.io

import life.qbic.repowiz.prepare.GeoParser
import life.qbic.repowiz.prepare.mapping.GeoMapper
import spock.lang.Specification

class XlsxParserSpecification extends Specification{

    def "geoparser"(){
        given:
        GeoParser parser = new XlsxParser()
        parser.parseTemplate("templates/seq_template_v2.1.xlsx")
        when:
        def res = parser.parseSheetByColor("METADATA TEMPLATE")
        then:
        res.keySet().size() == 7
        assert res.containsKey("SERIES")
        assert res.containsKey("SAMPLES")
        assert res.containsKey("PROTOCOLS")
        assert res.containsKey("DATA PROCESSING PIPELINE")
        assert res.containsKey("PROCESSED DATA FILES")
        assert res.containsKey("RAW FILES")
        assert res.containsKey("PAIRED-END EXPERIMENTS")

    }

}
