package life.qbic.repowiz

/**
 * This class serves as a wrapper to create a Repository DTO
 *
 * This class should be used whenever a {@link Repository} needs to be created.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class RepositoryCreator {
    private final Map data

    /**
     * Creates an instance of the creator to be able to create a Repository
     *
     * @param data as a Map containing repository data
     */
    RepositoryCreator(Map data) {
        this.data = data
    }

    /**
     * Creates a repository DTO with specific parameters which need to be part of the object
     *
     * @return a repository DTO
     *
     * @see Repository
     */
    Repository create() {
        return new Repository(repositoryName: (String) data.get("repositoryName"),
                dataType: data.get("dataType"),
                uploadTypes: (List) data.get("uploadTypes"),
                uploadFormat: (String) data.get("uploadFormat"),
                uploadRequirements: (List) data.get("uploadRequirements"),
                subsequentSteps: (List) data.get("subsequentSteps"))
    }


}
