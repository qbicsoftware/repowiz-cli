package life.qbic.repowiz.finalize

import life.qbic.repowiz.finalise.RepositoryLoader
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider
import life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider
import spock.lang.Specification

class RepositoryLoaderSpecification extends Specification{

    def "loader loads correct classes"(){
        given:
        RepositoryLoader loader = new RepositoryLoader()
        String geoClass = "life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider"

        when:
        def res = loader.getClassInstance(geoClass)

        then:
        res instanceof GeoTargetRepositoryProvider
        assert res instanceof TargetRepositoryProvider

    }
}
