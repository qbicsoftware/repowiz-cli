package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.spi.TargetRepository
import spock.lang.Specification

class RepositoryDatabaseConnectorSpecification extends Specification {

    RepositoryDatabaseConnector connector = new RepositoryDatabaseConnector(new TargetRepository())

    def "valid repo file parsing"() {
        when:
        InputStream stream = RepositoryDatabaseConnector.class.getClassLoader().getResourceAsStream("repositories/geo.json")
        Repository repo = connector.parseRepo(stream)

        then:
        repo.repositoryName == "Geo"
        repo.uploadTypes == ["affymetrix_GE", "hts"]
    }

    def "find repository file"() {
        when:
        InputStream stream = RepositoryDatabaseConnector.class.getClassLoader().getResourceAsStream("repositories/geo.json")
        Repository res = connector.parseRepo(stream)

        then:
        res.repositoryName == "Geo"
    }

    /* def "multiple repositories are found"(){
         when:
         List res = connector.findRepositories(["clinvar","geo"])
         then:
         res.size() == 2
         assert res.each {
             if(it.repositoryName == "Geo" || it.repositoryName == "ClinVar")
                 true
         }
     }
 
     def "invalid repository not found"(){
         when:
         Repository res = connector.findRepository("ega")
         then:
         res == null
     }
 
     def "upper_case repository is found"(){
         when:
         Repository res = connector.findRepository("GEO")
         then:
         res.repositoryName == "Geo"
     }*/
}
