package life.qbic.repowiz.io


import spock.lang.Specification

class XlsxParserSpecification extends Specification{

   /* def "Geo Parser returns all Fields from Template"(){
        given:
        GeoParser parser = new GeoParser()
        parser.parseAsStream("templates/seq_template_v2.1.xlsx")

        when:
        def res = parser.parseTemplateSheet("METADATA TEMPLATE")

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

    def "blbl"(){
        given:
        GeoParser parser = new GeoParser()
        parser.parseAsStream("templates/seq_template_v2.1.xlsx")

        when:
        parser.parseTemplateSheet("METADATA TEMPLATE")

        println parser.requiredFields
        //todo check if all fields are found!

        then:
        parser.requiredFields.contains("project title")
        assert parser.requiredFields.contains("characteristics: tag")
        assert parser.requiredFields.contains("molecule")
        assert !parser.requiredFields.contains("growth protocol")
        assert !parser.requiredFields.contains("supplementary file")
        //assert parser.requiredFields.size() == 31
    }

    def "Finds correct Level RGB color"(){
        given:
        GeoParser parser = new GeoParser()
        parser.parseAsStream("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(6).getCell(0)

        when:
        def color = parser.getRGBColor(cell)

        then:
        color == (byte []) [-1,0,0]

    }

    def "Finds correct Field RGB color"(){
        given:
        GeoParser parser = new GeoParser()
        parser.parseAsStream("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(8).getCell(0)

        when:
        def color = parser.getRGBColor(cell)

        then:
        color == (byte []) [0,0,-1]

    }

    def "Detect required fields from comment"(){
        given:
        GeoParser parser = new GeoParser()
        parser.parseAsStream("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(11).getCell(0)

        when:
        def required = parser.isRequired(cell)

        then:
        required
    }

    def "Detect optional fields in comment"(){
        given:
        GeoParser parser = new GeoParser()
        parser.parseAsStream("templates/seq_template_v2.1.xlsx")

        XSSFCell cell = parser.wb.getSheetAt(0).getRow(14).getCell(0)

        when:
        def required = parser.isRequired(cell)

        then:
        !required
    }*/


}
