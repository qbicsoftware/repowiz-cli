import life.qbic.repowiz.mapping.GeoMapper
import spock.lang.Specification

class GeoMapperSpecification extends Specification{

    def "maps repoWiz term to correct geoTerm"(){
        when:
        GeoMapper mapper = new GeoMapper()
        def res = mapper.getGeoTerm("project title")

        then:
        res == "series_title"
    }

    def "maps geoTerm to correct repoWizTerm"(){
        when:
        GeoMapper mapper = new GeoMapper()
        def res = mapper.getRepoWizTerm("series_title")

        then:
        res == "project title"
    }

    def "map characteristics to geoterm"(){
        when:
        GeoMapper mapper = new GeoMapper()
        def res = mapper.getGeoTerm("characteristic genotype")

        then:
        res == "samples_characteristic genotype"
    }

    def "map characteristics to repoWizterm"(){
        when:
        GeoMapper mapper = new GeoMapper()
        def res = mapper.getRepoWizTerm("samples_characteristic genotype")

        then:
        res == "characteristic genotype"
    }

}
