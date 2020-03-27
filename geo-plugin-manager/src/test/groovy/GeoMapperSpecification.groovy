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

}
