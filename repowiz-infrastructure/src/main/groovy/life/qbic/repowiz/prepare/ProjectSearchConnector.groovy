package life.qbic.repowiz.prepare

import life.qbic.repowiz.prepare.projectSearch.ProjectSearchInput


class ProjectSearchConnector implements ProjectSearchInput{

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
    def getDataForSamples(String sampleID) {
        return null
    }
}
