package life.qbic.repowiz.prepare


interface ProjectSearchService {

    HashMap<String,String> getProjectMetadata(String projectID)
    HashMap<String,String> getExperimentMetadata(String experimentID)
    HashMap<String,String> getSampleMetadata(String projectID)
    def getFilesForSamples(String sampleID)

}
