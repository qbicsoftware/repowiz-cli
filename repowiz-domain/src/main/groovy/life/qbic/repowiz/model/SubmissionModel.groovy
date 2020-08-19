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
    //do not delete this fields, they are used during runtime!
    public List<String> missingValues
    String uploadType

    /**
     * Creates a submission model based on a project and a list of samples
     * @param project for which the submission is created
     * @param samples of the project
     */
    SubmissionModel(RepoWizProject project, List<RepoWizSample> samples) {
        this.project = project
        this.samples = samples
    }

    /**
     * Collects the properties from the project and all samples
     * @return a map with the submission properties
     */
    HashMap getAllProperties() {
        HashMap properties = new HashMap()
        //do so to not overwrite project properties!!
        properties << project.projectProperties

        samples.each { sample ->
            properties << sample.sampleProperties
        }
        return properties
    }

    /**
     * Returns a hashmap of properties of the project
     * @return project properties
     */
    HashMap<String, String> projectProperties() {
        return project.projectProperties as HashMap<String, String>
    }

    /**
     * Returns a hashmap of the properties of the samples
     * @return sample properties
     */
    HashMap<String, HashMap<String, String>> sampleProperties() {
        HashMap<String, HashMap<String, String>> sampleProperties = new HashMap<>()

        samples.each { sample ->
            sampleProperties.put(sample.sampleName, sample.sampleProperties)
        }
        return sampleProperties
    }

}
