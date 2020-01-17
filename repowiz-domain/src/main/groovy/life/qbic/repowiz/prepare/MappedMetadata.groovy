package life.qbic.repowiz.prepare

interface MappedMetadata {

    List<String> mapMetadataToRepository(String repository, List<String> metadataIdentifier)

}