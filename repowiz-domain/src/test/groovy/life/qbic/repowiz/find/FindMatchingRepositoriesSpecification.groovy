package life.qbic.repowiz.find

import life.qbic.repowiz.DatabaseProjectInformation
import life.qbic.repowiz.RepositoryDescription
import spock.lang.Specification

class FindMatchingRepositoriesSpecification extends Specification {

    def mockedMatchingRepositoriesOutput = Mock(MatchingRepositoriesOutput)
    def mockedProjectSpecification = Mock(DatabaseProjectInformation)
    def mockedRepositoryDescription = Mock(RepositoryDescription)

    def findMatchingRepositories = new FindMatchingRepositories(mockedMatchingRepositoriesOutput,mockedProjectSpecification,mockedRepositoryDescription)

    def "wrong project identifier is detected as wrong"(){
        given:
        def projectCode = "XXXXX"

        when:
        def valid = findMatchingRepositories.isProjectIdValid(projectCode)

        then:
        !valid
    }

    def "wrong project identifier produces warning"(){
        given:
        def projectCode = "XXXXX"

        when:
        def valid = findMatchingRepositories.isProjectIdValid(projectCode)

        then:
        thrown(IllegalArgumentException)
    }

    def "correct project identifier produces is detected as valid"(){
        given:
        def projectCode = "QXXXX"

        when:
        def valid = findMatchingRepositories.isProjectIdValid(projectCode)

        then:
        valid
    }

    def "no repository shall be specified"(){ //only execute this usecase when there is no repository specified
        when:
        def out = findMatchingRepositories.getRepositoryInfo(null)

        then:
        out = null
    }

}
