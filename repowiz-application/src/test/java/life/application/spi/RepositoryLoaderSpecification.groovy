package life.application.spi

import life.qbic.repowiz.application.spi.RepositoryLoader
import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider
import spock.lang.Specification

class RepositoryLoaderSpecification extends Specification{

    def "loader instantiates concrete classes"(){
        given:
        RepositoryLoader loader = new RepositoryLoader()
        String geoClass = "life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider"

        when:
        def res = loader.getClassInstance(geoClass)

        then:
        res instanceof TargetRepositoryProvider

    }

    def "loader loads all classes"(){
        given:
        RepositoryLoader loader = new RepositoryLoader()

        when:
        def res = loader.load()

        then:
        assert res.size() == 2

    }

    def "loader loads geo target class"(){
        given:
        RepositoryLoader loader = new RepositoryLoader()
        String geoClass = "life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider"

        when:
        def res = loader.load(geoClass)
        SubmissionManager manager = res.create()

        then:
        res instanceof TargetRepositoryProvider
        assert manager != null
    }

    def "loader loads clinvar target class"(){
        given:
        RepositoryLoader loader = new RepositoryLoader()
        String clinvarClass = "life.qbic.repowiz.spiimpl.ClinvarTargetRepositoryProvider"

        when:
        def res = loader.load(clinvarClass)
        SubmissionManager manager = res.create()

        then:
        res instanceof TargetRepositoryProvider
        assert manager != null
    }
}
