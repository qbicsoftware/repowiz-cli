package life.qbic.repowiz.submissionTypes

import life.qbic.repowiz.download.GeoSubmissionDownloader

abstract class GeoSubmission {

    abstract String templatePath
    abstract List<String> missingFields = []
    abstract GeoSubmissionDownloader downloader

   GeoSubmission() {
       downloader = new GeoSubmissionDownloader()
   }


    abstract void parseTemplate()
    //method to determine which fields from the template are required to be valid
    abstract List<String> determineMissingFields(Map submissionFields)
    abstract void writeToWorkbook(HashMap values)

}
