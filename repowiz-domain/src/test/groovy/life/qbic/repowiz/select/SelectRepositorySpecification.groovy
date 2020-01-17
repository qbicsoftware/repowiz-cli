package life.qbic.repowiz.select

import life.qbic.repowiz.DatabaseProjectInformation
import life.qbic.repowiz.RepositoryDescription
import spock.lang.Specification

class SelectRepositorySpecification extends Specification{

    def projectSpecification = Mock(DatabaseProjectInformation)
    def repositoryDescription = Mock(RepositoryDescription)
    def output = Mock(RepositorySpecificationOutput)

    def selectRepository = new SelectRepository(projectSpecification,repositoryDescription,output)

    def "If repository not found an exception is thrown"(){ //meaningful test?
        when:
        selectRepository.getRepositoryInfo(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "non-matching repository and data produce a warning"(){
        given:
        def suggestedRepos = ["ENA","SRA"]
        def definedRepo = "GEO"

        when:
        def result = selectRepository.repoAcceptsProjectData(suggestedRepos,definedRepo)

        then:
        thrown(IllegalArgumentException)

    }

    def "matching repository and data produce are detected"(){
        given:
        def suggestedRepos = ["ENA","SRA"]
        def definedRepo = "ENA"

        when:
        def result = selectRepository.repoAcceptsProjectData(suggestedRepos,definedRepo)

        then:
        result
    }


}
