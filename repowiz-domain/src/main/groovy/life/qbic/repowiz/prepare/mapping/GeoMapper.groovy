package life.qbic.repowiz.prepare.mapping


import life.qbic.repowiz.prepare.GeoParser

class GeoMapper implements MapInfoInput{

    GeoParser parser

    GeoMapper(GeoParser parser){
        this.parser = parser
    }

    @Override
    def getFields(String uploadType) {
        String templateName = ""
        List<String> sheets = []

        switch (uploadType){
            case "hts":
                //call method for hts template
                templateName = "seq_template_v2.1.xlsx"
                sheets.add("METADATA TEMPLATE")
                break
            case "affymetrix_GE":
                //whole gene expression
                //todo ask for platform accession number, if not provided chose template with platform fillout
                //
                templateName = ""
                sheets.add()
                break
        }

        //load template fields --> xlsx parser since all geo templates have this format
        //parser.parseAsStream(
        parser.parseTemplate(templateName)

        sheets.each { sheet ->
            parser.parseSheetByColor(sheet)
        }

        return null
    }
}
