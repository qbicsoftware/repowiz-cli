package life.qbic.repowiz.finalise.spi

import life.qbic.repowiz.finalise.api.SubmissionManager

interface TargetRepositoryProvider {
    //creates validated objects???
    //spi interface = exchange rate provider in baeldung beispiel
    SubmissionManager create()
}