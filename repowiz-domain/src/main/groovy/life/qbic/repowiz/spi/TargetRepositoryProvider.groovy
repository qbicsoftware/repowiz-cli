package life.qbic.repowiz.spi

import life.qbic.repowiz.Repository

abstract class TargetRepositoryProvider {
    //creates validated objects???
    //spi interface = exchange rate provider in baeldung beispiel
    abstract String getProviderName()
    abstract void setUploadType(String type)
    abstract SubmissionManager create()
    abstract Repository getRepositoryDescription()
}