package life.qbic.repowiz.prepare

interface MappedMetadata {

    String mapMetadataTerm(String repository, String metadataTerm)
    //or from database term to repowiz term and from repowiz term to repository term

}