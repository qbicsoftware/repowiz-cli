package life.qbic.repowiz.submissionTypes

abstract class GeoSubmission {

    private abstract String templatePath

    GeoSubmission(String templatePath){
       this.templatePath = templatePath
    }

    abstract void parseTemplate()
    //method to determine which fields from the template are required to be valid
    abstract List<String> extractRequiredFields()
    //method to determine which fields are contained within the template and thus are "valid"
    abstract List<String> extractValidFields()


}
