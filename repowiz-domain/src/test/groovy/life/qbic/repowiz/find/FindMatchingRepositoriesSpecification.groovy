package life.qbic.repowiz.find


import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.tree.DecisionTree
import spock.lang.Specification

class FindMatchingRepositoriesSpecification extends Specification {

    def mockedMatchingRepositoriesOutput = Mock(MatchingRepositoriesOutput)
    def mockedRepositoryDescription = Mock(RepositoryDescription)

    def findMatchingRepositories = new FindMatchingRepositories(mockedMatchingRepositoriesOutput,mockedRepositoryDescription)

    def "correct submission type specifications leads to correct repository"(){
        given:
        DecisionTree tree = new DecisionTree()

        HashMap<String,String> map = new HashMap<>()
        map.put("organism","human")
        map.put("access_type","open")
        map.put("data_type","dna_rna")
        map.put("experiment_type","raw_reads")

        when:
        List<String> res =  tree.findRepository(map)

        then:
        ["ena","sra"].sort() == res.sort()
    }

    def "wrong submission type specification leads to error"(){
        given:
        DecisionTree tree = new DecisionTree()

        HashMap<String,String> map = new HashMap<>()
        map.put("organism","human")
        map.put("data_type","dna_rna")
        map.put("experiment_type","raw_reads")

        when:
        tree.findRepository(map)

        then:
        thrown(NullPointerException)
    }

   /**
    * def "wrong project identifier is detected as wrong"(){
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
*/



}
