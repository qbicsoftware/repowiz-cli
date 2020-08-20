package life.qbic.repowiz.model

/**
 * This class serves as DTO for representing the submission model
 *
 * A submission model consists of a project, a list of samples, a list of missing values and an upload type.
 * It describes all elements of a submission that were collected and which are required but not part of a project.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class SubmissionModel {
    final RepoWizProject project
    final List<RepoWizSample> samples

    private List<String> missingValues
    private String uploadType

    /**
     * Creates a submission model based on a project and a list of samples
     *
     * @param project for which the submission is created
     * @param samples of the project
     */
    SubmissionModel(RepoWizProject project, List<RepoWizSample> samples) {
        this.project = project
        this.samples = samples
    }

    /**
     * Collects the properties from the project and all samples
     *
     * @return a map with the submission properties
     */
    Map getAllProperties() {
        Map properties = new HashMap()
        //do so to not overwrite project properties!!
        properties << project.projectProperties

        samples.each { sample ->
            properties << sample.sampleProperties
        }
        return properties
    }

    /**
     * Returns a hashmap of properties of the project
     *
     * @return project properties
     */
    Map<String, String> projectProperties() {
        return project.projectProperties as HashMap<String, String>
    }

    /**
     * Returns a hashmap of the properties of the samples
     *
     * @return sample properties
     */
    HashMap<String, HashMap<String, String>> sampleProperties() {
        HashMap<String, HashMap<String, String>> sampleProperties = new HashMap<>()

        samples.each { sample ->
            sampleProperties.put(sample.sampleName, sample.sampleProperties)
        }
        return sampleProperties
    }

    /**
     * Returns the class variable storing missing values of a submission
     *
     * @return list of missing values
     */
    List<String> getMissingValues() {
        return missingValues
    }

    /**
     * Stores a list of missing values of a submission. This method should be used when the submission has been validated
     * and it is determined what values were missing in the submission
     *
     * @param missingValues is a list of values missing in the submission
     */
    void setMissingValues(List<String> missingValues) {
        this.missingValues = missingValues
    }

    /**
     * States the type of submission that is uploaded. For GEO this would be e.g. HTS if the submission
     * is a high-throughput submission
     *
     * @return the upload type of a submission
     */
    String getUploadType() {
        return uploadType
    }

    /**
     * Sets the type of submission that is uploaded. For GEO this would be e.g. HTS if the submission
     * is a high-throughput submission
     * This method should be used when the upload type is defined by the user.
     *
     * @param uploadType describes of what type the submission is
     */
    void setUploadType(String uploadType) {
        this.uploadType = uploadType
    }
}
