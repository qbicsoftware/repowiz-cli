package life.qbic.repowiz.prepare.projectSearch

/**
 * Interface that defines how properties are mapped to be used in RepoWiz
 *
 * In order to handle properties of external databases they must be mapped so that they can be translated into
 * RepoWiz terms. Beside the mapping duplicate terms must also be masked to be able to distinguish them.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface Mapper {

    /**
     * Function to map properties foreign terms to RepoWiz terms
     * @param properties
     * @return
     */
    HashMap mapProperties(Map properties)

    /**
     * Properties that are duplicates must be masked
     * @param mask
     * @param properties
     * @return
     */
    HashMap maskDuplicateProperties(String mask, Map properties)

}