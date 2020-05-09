package life.qbic.repowiz.submissionTypes

import life.qbic.repowiz.model.GeoSample
import life.qbic.repowiz.download.GeoSubmissionDownloader

/**
 * Class describing the general form of all GEO submissions.
 * Each submission must extend this class
 */
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
    abstract void writeProjectToWorkbook(HashMap<String,String> project)
    abstract void writeSampleToWorkbook (List<GeoSample> samples)
    abstract void downloadFile(String fileName)

}
