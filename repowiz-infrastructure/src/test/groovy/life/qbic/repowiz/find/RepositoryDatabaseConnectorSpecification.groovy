package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.io.JsonParser
import spock.lang.Specification

class RepositoryDatabaseConnectorSpecification extends Specification{

    RepositoryDatabaseConnector connector = new RepositoryDatabaseConnector()

    def "valid repo file parsing"(){
        given:
        //InputStream fileStream = connector.getClass().getResourceAsStream("/repositories/geo.json")
        JsonParser parser = new JsonParser()
        def repoInfo = parser.parseAsStream("repositories/geo.json")

        when:
        assert repoInfo instanceof Map
        Repository repo = connector.getRepository("repositories/geo.json")
        println repo
        //print repoInfo

        then:
        repo.repositoryName == "Geo"
        repo.uploadTypes == ["affymetrix_GE", "hts"]
    }

    def "find repository file"(){
        when:
        def res = connector.findRepository(["geo"])
        then:
        res.get(0).repositoryName == "Geo"
    }

    def "multiple repositories are found"(){
        when:
        def res = connector.findRepository(["clinvar","geo"])
        then:
        res.size() == 2
        res.each {
            if(it.repositoryName == "Geo" ||it.repositoryName == "ClinVar")
                true
        }
    }

    def "invalid repository not found"(){
        when:
        def res = connector.findRepository(["ega"])
        then:
        res.empty
    }

    def "upper_case repository not found"(){
        when:
        def res = connector.findRepository(["GEO"])
        then:
        res.empty
    }
}
