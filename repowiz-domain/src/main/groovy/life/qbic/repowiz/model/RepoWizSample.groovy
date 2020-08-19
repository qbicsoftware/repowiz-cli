package life.qbic.repowiz.model

/**
 * This class serves as DTO for representing scientific samples
 *
 * It should be used whenever samples need to be used within RepoWiz.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class RepoWizSample {

    final String sampleName
    final HashMap<String, String> sampleProperties


    /**
     * Creates a RepoWizSample based on a sample name and its properties
     * @param name describing the sample (e.g. code)
     * @param sampleProperties describing the samples properties
     */
    RepoWizSample(String name, HashMap sampleProperties) {
        sampleName = name
        this.sampleProperties = sampleProperties
    }

    //todo do not implement any logic in here!!!!
}
