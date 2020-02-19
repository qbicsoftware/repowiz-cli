package life.qbic.repowiz.prepare.mapping

class GeoMapper implements MapInfoInput{

    GeoParser parser
    MapInfoOutput output

    GeoMapper(GeoParser parser){
        this.parser = parser
    }


    @Override
    def addOutput(MapInfoOutput output) {
        this.output = output
    }

    @Override
    HashMap<String,HashMap> getFields(String uploadType) { //, MapInfoOutput out
        String templateName = "templates/"
        List<String> sheets = []

        switch (uploadType){
            case "hts":
                //call method for hts template
                templateName += "seq_template_v2.1.xlsx"
                sheets.add("METADATA TEMPLATE")
                break
            case "affymetrix_GE":
                //whole gene expression
                //todo ask for platform accession number, if not provided chose template with platform fillout
                templateName = ""
                sheets.add("")
                break
        }

        println templateName
        parser.parseTemplate(templateName)

        HashMap<String,HashMap> fieldsPerSheet = new HashMap<>()
        sheets.each { sheet ->
            fieldsPerSheet.put(sheet,parser.parseSheetByColor(sheet))
        }

        //todo only do one of these!
        output.transferFields(fieldsPerSheet)

        return fieldsPerSheet
    }

}
