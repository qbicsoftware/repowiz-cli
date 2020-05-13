package life.qbic.repowiz.prepare.projectSearch

/**
 * Interface that defines
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