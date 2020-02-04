package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.utils.IO
import spock.lang.Specification

class RepositoryDatabaseConnectorSpecification extends Specification{

    RepositoryDatabaseConnector connector = new RepositoryDatabaseConnector()

    def "valid repo file parsing"(){
        given:
        InputStream fileStream = connector.getClass().getResourceAsStream("/repositories/geo.json")
        def repoInfo = IO.parseJsonStream(fileStream)

        when:
        assert repoInfo instanceof Map
        Repository repo = connector.createRepoFromJSON(repoInfo)
        print repoInfo

        then:
        repo.name == "Geo"
        repo.experimentTypes == ["Microarray","HTS","other (NanoString, RT-PCR, SAGE)"]


    }
}
