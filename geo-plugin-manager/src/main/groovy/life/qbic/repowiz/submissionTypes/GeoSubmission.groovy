package life.qbic.repowiz.submissionTypes

abstract class GeoSubmission {

    abstract String templatePath

    abstract void parseTemplate()
    //method to determine which fields from the template are required to be valid
    abstract List<String> determineMissingFields(List submissionFields)

}
