package life.qbic.repowiz

interface ProjectDetails {

    List<String> getSamples(String projectID)
    HashMap<String,String> getMetadataPerSample(String sampleID)
    List<File> getFilesForSamples(String sampleID)

}
