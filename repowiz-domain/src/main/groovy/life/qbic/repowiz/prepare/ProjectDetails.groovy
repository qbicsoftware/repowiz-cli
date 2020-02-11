package life.qbic.repowiz.prepare

import java.nio.file.Path

interface ProjectDetails {

    HashMap<String,String> getProjectMetadata(String projectID)
    HashMap<String,String> getExperimentMetadata(String experimentID)
    HashMap<String,String> getSampleMetaData(String projectID)
    Path getFilesForSamples(String sampleID)

}
