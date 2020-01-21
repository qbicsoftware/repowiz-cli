package life.qbic.repowiz.submit

interface SubmissionOutput {

    def getSampleIds(List<String> sampleIDs)
    def getMetaDataForSamples(HashMap<String,String> metadata, String sampleID)
    def getFilesForSamples(List<String> filePaths, String sampleID)
    def uploadFormat(String type)
    List<String> subsequentSteps()
    String submissionIdentifier()

}