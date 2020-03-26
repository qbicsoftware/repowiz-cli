package life.qbic.repowiz.submissionTypes

class GeoHtsSubmission extends GeoSubmission{

    String sheetName = "METADATA TEMPLATE"

    GeoHtsSubmission(){
        super.templatePath = "templates/seq_template_v2.1.xlsx"
    }

    @Override
    void parseTemplate() {

    }

    @Override
    List<String> determineMissingFields(List submissionFields) {
        return null
    }


    void validFields() {

    }

    void requiredFields() {

    }

}
