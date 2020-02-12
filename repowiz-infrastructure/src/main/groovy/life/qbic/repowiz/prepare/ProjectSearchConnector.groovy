package life.qbic.repowiz.prepare

import java.nio.file.Path

class ProjectSearchConnector implements ProjectSearchService{



    @Override
    HashMap<String, String> getProjectMetadata(String projectID) {
        return null
    }

    @Override
    HashMap<String, String> getExperimentMetadata(String experimentID) {
        return null
    }

    @Override
    HashMap<String, String> getSampleMetadata(String projectID) {
        return null
    }

    @Override
    Path getFilesForSamples(String sampleID) {
        return null
    }
}
