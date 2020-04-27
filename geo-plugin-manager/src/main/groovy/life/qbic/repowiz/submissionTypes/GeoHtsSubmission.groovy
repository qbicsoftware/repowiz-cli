package life.qbic.repowiz.submissionTypes

import life.qbic.repowiz.download.GeoSubmissionDownloader
import life.qbic.repowiz.mapping.GeoTemplateParser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class GeoHtsSubmission extends GeoSubmission{

    String sheetName = "METADATA TEMPLATE"

    //fields with restricted values
    //List seq_type = ["single","paired-end"]

    private GeoTemplateParser parser
    private static final Logger LOG = LogManager.getLogger(GeoHtsSubmission.class)

    GeoHtsSubmission(){
        super.templatePath = "templates/seq_template_v2.1.xlsx"
        parser = new GeoTemplateParser()
        parseTemplate()
    }

    @Override
    void parseTemplate() {
        parser.createWorkbook(super.templatePath)
        parser.parseTemplateSheet(sheetName)
    }

    @Override
    List<String> determineMissingFields(Map filledFields) {
        parser.requiredFields.each {requiredField ->
            if(!filledFields.keySet().contains(requiredField)){
                if(!(super.missingFields.contains(requiredField))) super.missingFields << requiredField
            }
        }

        super.missingFields.addAll(containsOtherRequiredFields(filledFields))

        return super.missingFields
    }

    @Override
    void writeToWorkbook(HashMap values) {
        parser.writeToWorkbook(values)
    }

    def markMissingFieldsInTemplate(){

    }

//check if other required fields, that are not marked with a comment are contained within the given fields
    static List containsOtherRequiredFields(Map fields){
        List missing = []
        //some other fields are also required but not marked with a comment
        List required = ["raw files_file name","processed data files_file name"]
        List required_pairedEnd = ["paired-end experiments_file name 1","paired-end experiments_file name 2"]

        required.each {field ->
            if(!fields.keySet().contains(field)) missing << field
        }

        if(fields.containsKey("raw files_single or paired-end") && fields.get("raw files_single or paired-end") == "paired-end"){
            if(!fields.keySet().contains(required_pairedEnd[0])) missing << required_pairedEnd[0]
            if(!fields.keySet().contains(required_pairedEnd[1])) missing << required_pairedEnd[1]
        }

        return missing
    }

}
