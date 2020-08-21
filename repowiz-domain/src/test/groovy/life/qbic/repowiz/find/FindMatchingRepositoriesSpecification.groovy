package life.qbic.repowiz.find


import life.qbic.repowiz.RepositoryDescription
import spock.lang.Specification

class FindMatchingRepositoriesSpecification extends Specification {

    def mockedMatchingRepositoriesOutput = Mock(MatchingRepositoriesOutput)
    def mockedRepositoryDescription = Mock(RepositoryDescription)

    def findMatchingRepositories = new FindMatchingRepositories(mockedMatchingRepositoriesOutput, mockedRepositoryDescription)

    def "suggests the correct organisms"() {
        when:
        findMatchingRepositories.startGuide()
        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["human", "other", "environmental community", "plants"].sort()
    }

    def "wrong name"() {
        when:
        boolean res = findMatchingRepositories.validateDecision("HUUMAN")

        then:
        !res
    }

    def "case sensitive"() {
        when:
        boolean res = findMatchingRepositories.validateDecision("Human")

        then:
        !res
    }


    def "suggest access type for human as organism"() {
        when:
        findMatchingRepositories.validateDecision("human")
        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["open access", "controlled access"].sort()
    }

    def "suggest data type for other as organism"() {
        when:
        findMatchingRepositories.validateDecision("other")
        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["variants", "expression data", "dna/rna", "protein"].sort()
    }

    def "suggest experiment type for other"() {
        given:
        findMatchingRepositories.validateDecision("other")

        when:
        findMatchingRepositories.validateDecision("variants")

        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["structural variants", "genetic variants"].sort()
    }

    def "suggest repository type for other,variants,structural"() {
        given:
        findMatchingRepositories.validateDecision("other")
        findMatchingRepositories.validateDecision("variants")

        when:
        findMatchingRepositories.validateDecision("structural variants")

        def node = findMatchingRepositories.currentDecisionLevel
        def res = findMatchingRepositories.tree.getChildrenData(node)

        then:
        res.sort() == ["eva", "dbvar"].sort()
    }

}
