package life.qbic.repowiz.prepare.mapping

import life.qbic.repowiz.prepare.parsing.GeoParserInput
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class GeoSubmission implements MapInfoInput{

    private static final Logger LOG = LogManager.getLogger(GeoSubmission.class)

    GeoParserInput parser
    MapInfoOutput output

    GeoSubmission(GeoParserInput parser){
        this.parser = parser
    }

    @Override
    def addOutput(MapInfoOutput output) {
        this.output = output
    }

    @Override
    def getFields(String uploadType) {
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

        LOG.info "Parsing GEO template ..."
        parser.parseTemplate(templateName)

        HashMap<String,HashMap> fieldsPerSheet = new HashMap<>()

        sheets.each { sheet ->
            fieldsPerSheet.put(sheet,parser.parseHtsSheet(sheet))
        }

        output.transferFields(fieldsPerSheet)
    }


}
