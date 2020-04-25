package life.qbic.repowiz.prepare.projectSearch

interface Mapper {

    HashMap mapProperties(Map properties)
    HashMap maskDuplicateProperties(String type, Map properties)

}