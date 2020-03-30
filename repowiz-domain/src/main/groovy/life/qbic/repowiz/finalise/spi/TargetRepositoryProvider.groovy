package life.qbic.repowiz.finalise.spi

import life.qbic.repowiz.finalise.api.SubmissionManager

abstract class TargetRepositoryProvider {
    //creates validated objects???
    //spi interface = exchange rate provider in baeldung beispiel
    abstract String getProviderName()
    abstract void setUploadType(String type)
    abstract SubmissionManager create()
}