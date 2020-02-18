package life.qbic.repowiz.io

import life.qbic.repowiz.prepare.GeoParser
import life.qbic.repowiz.prepare.mapping.GeoMapper
import org.apache.poi.xssf.usermodel.XSSFCell
import spock.lang.Specification

class XlsxParserSpecification extends Specification{

    def "Geo Parser returns all Fields from Template"(){
        given:
        GeoParser parser = new XlsxParser()
        parser.parseTemplate("templates/seq_template_v2.1.xlsx")

        when:
        def res = parser.parseSheetByColor("METADATA TEMPLATE")
        print res

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

    def "Finds correct Level RGB color"(){
        given:
        GeoParser parser = new XlsxParser()
        parser.parseTemplate("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(6).getCell(0)

        when:
        def color = parser.getRGBColor(cell)

        then:
        color == (byte []) [-1,0,0]

    }
    def "Finds correct Field RGB color"(){
        given:
        GeoParser parser = new XlsxParser()
        parser.parseTemplate("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(8).getCell(0)

        when:
        def color = parser.getRGBColor(cell)

        then:
        color == (byte []) [0,0,-1]

    }


}
