import life.qbic.repowiz.mapping.GeoTemplateParser
import org.apache.poi.xssf.usermodel.XSSFCell
import spock.lang.Specification

class XlsxParserSpecification extends Specification {

    /*def "Geo Parser returns all Fields from Template"(){
         given:
         GeoTemplateParser parser = new GeoTemplateParser()
         parser.parseAsStream("templates/seq_template_v2.1.xlsx")
 
         when:
         parser.parseTemplateSheet("METADATA TEMPLATE")
 
         then:
         parser.templateFields.keySet().size() == 7
         assert parser.templateFields.containsKey("SERIES")
         assert parser.templateFields.containsKey("SAMPLES")
         assert parser.templateFields.containsKey("PROTOCOLS")
         assert parser.templateFields.containsKey("DATA PROCESSING PIPELINE")
         assert parser.templateFields.containsKey("PROCESSED DATA FILES")
         assert parser.templateFields.containsKey("RAW FILES")
         assert parser.templateFields.containsKey("PAIRED-END EXPERIMENTS")
 
     }*/

    def "blbl"() {
        given:
        GeoTemplateParser parser = new GeoTemplateParser()
        parser.createWorkbook("templates/seq_template_v2.1.xlsx")

        when:
        parser.parseTemplateSheet("METADATA TEMPLATE")

        println parser.requiredFields

        then:
        parser.requiredFields.contains("series_title")
        assert parser.requiredFields.contains("samples_characteristics: tag")
        assert parser.requiredFields.contains("samples_molecule")
        assert !parser.requiredFields.contains("protocols_growth protocol")
    }

    def "Finds correct Level RGB color"() {
        given:
        GeoTemplateParser parser = new GeoTemplateParser()
        parser.createWorkbook("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(6).getCell(0)

        when:
        def color = parser.getRGBColor(cell)

        then:
        color == (byte[]) [-1, 0, 0]
    }

    def "Finds correct Field RGB color"() {
        given:
        GeoTemplateParser parser = new GeoTemplateParser()
        parser.createWorkbook("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(8).getCell(0)

        when:
        def color = parser.getRGBColor(cell)

        then:
        color == (byte[]) [0, 0, -1]

    }

    def "Detect required fields from comment"() {
        given:
        GeoTemplateParser parser = new GeoTemplateParser()
        parser.createWorkbook("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(11).getCell(0)

        when:
        def required = parser.isRequired(cell)

        then:
        required
    }

    def "Detect optional fields in comment"() {
        given:
        GeoTemplateParser parser = new GeoTemplateParser()
        parser.createWorkbook("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(14).getCell(0)

        when:
        def required = parser.isRequired(cell)

        then:
        !required
    }


}
