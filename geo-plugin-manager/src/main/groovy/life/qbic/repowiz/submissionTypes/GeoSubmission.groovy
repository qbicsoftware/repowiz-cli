package life.qbic.repowiz.submissionTypes

import life.qbic.repowiz.download.GeoSubmissionDownloader

abstract class GeoSubmission {

    String templatePath
    List<String> missingFields = []
    GeoSubmissionDownloader downloader

   GeoSubmission() {
       downloader = new GeoSubmissionDownloader()
   }


    abstract void parseTemplate()
    //method to determine which fields from the template are required to be valid
    abstract List<String> determineMissingFields(Map submissionFields)
    abstract def markMissingFieldsInTemplate()
    abstract void writeToWorkbook(HashMap<String,String> project, List<HashMap<String,String>> samples)
    abstract void downloadFile(String fileName)

}
