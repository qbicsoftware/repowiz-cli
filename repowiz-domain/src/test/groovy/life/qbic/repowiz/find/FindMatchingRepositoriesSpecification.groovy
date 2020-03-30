package life.qbic.repowiz.find


import life.qbic.repowiz.RepositoryDescription
import spock.lang.Specification

class FindMatchingRepositoriesSpecification extends Specification {

    def mockedMatchingRepositoriesOutput = Mock(MatchingRepositoriesOutput)
    def mockedRepositoryDescription = Mock(RepositoryDescription)

    def findMatchingRepositories = new FindMatchingRepositories(mockedMatchingRepositoriesOutput,mockedRepositoryDescription)

    def "suggests the correct organisms"(){
        when:
        findMatchingRepositories.startGuide()
        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["human", "other", "environmental community", "plants"].sort()
    }

    def "wrong name"(){
        when:
        boolean res = findMatchingRepositories.validateDesicion("HUUMAN")

        then:
        !res
    }

    def "case sensitive"(){
        when:
        boolean res = findMatchingRepositories.validateDesicion("Human")

        then:
        !res
    }


    def "suggest access type for human as organism"(){
        when:
        findMatchingRepositories.validateDesicion("human")
        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["open access","controlled access"].sort()
    }

    def "suggest data type for other as organism"(){
        when:
        findMatchingRepositories.validateDesicion("other")
        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["variants","expression data","dna/rna","protein"].sort()
    }

    def "suggest experiment type for other"(){
        given:
        findMatchingRepositories.validateDesicion("other")

        when:
        findMatchingRepositories.validateDesicion("variants")

        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["structural variants","genetic variants"].sort()
    }

    def "suggest repository type for other,variants,structural"(){
        given:
        findMatchingRepositories.validateDesicion("other")
        findMatchingRepositories.validateDesicion("variants")

        when:
        findMatchingRepositories.validateDesicion("structural variants")

        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["eva","dbvar"].sort()
    }


    /**
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
*/



}
