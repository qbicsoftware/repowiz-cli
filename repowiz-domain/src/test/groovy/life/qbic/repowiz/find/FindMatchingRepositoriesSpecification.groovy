package life.qbic.repowiz.find


import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import spock.lang.Specification

class FindMatchingRepositoriesSpecification extends Specification {

    def mockedMatchingRepositoriesOutput = Mock(MatchingRepositoriesOutput)
    def mockedRepositoryDescription = Mock(RepositoryDescription)

    def findMatchingRepositories = new FindMatchingRepositories(mockedMatchingRepositoriesOutput,mockedRepositoryDescription)

    def "valid submission specification produces a list of matching repositories"(){
        given:
        HashMap<String,String> submissionSpecification = new HashMap<>() //valid example
        //todo fill

        when:
        List<Repository> result = findMatchingRepositories.suggestRepos(submissionSpecification)
        //todo mock output of repository description

        then:
        result.size() > 0
    }

    def "if there is no matching repository the system gives a warning"(){
        given:
        HashMap<String,String> submissionSpecification = new HashMap<>()

        when:
        //something

        then:
        thrown(IllegalArgumentException)
    }


   /** def "wrong project identifier is detected as wrong"(){
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
    }*/

}
