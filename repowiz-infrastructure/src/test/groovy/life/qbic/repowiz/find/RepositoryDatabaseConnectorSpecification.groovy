package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.io.JsonParser
import spock.lang.Specification

class RepositoryDatabaseConnectorSpecification extends Specification{

    RepositoryDatabaseConnector connector = new RepositoryDatabaseConnector()

    def "valid repo file parsing"(){
        given:
        InputStream fileStream = connector.getClass().getResourceAsStream("/repositories/geo.json")
        def repoInfo = JsonParser.parseStream(fileStream)

        when:
        assert repoInfo instanceof Map
        Repository repo = connector.createRepoFromJSON(repoInfo)
        print repoInfo

        then:
        repo.name == "Geo"
        repo.experimentTypes == ["Microarray","HTS","other (NanoString, RT-PCR, SAGE)"]


    }

    def "find repository file"(){
        when:
        def res = connector.findRepository(["geo"])
        then:
        res.get(0).name == "Geo"
    }

    def "multiple repositories are found"(){
        when:
        def res = connector.findRepository(["clinvar","geo"])
        then:
        res.size() == 2
        res.each {
            if(it.name == "Geo" ||it.name == "ClinVar")
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
