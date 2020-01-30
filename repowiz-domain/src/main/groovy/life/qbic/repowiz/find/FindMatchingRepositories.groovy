package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.find.tree.DecisionTree
import life.qbic.repowiz.find.tree.Node

class FindMatchingRepositories implements FindMatchingRepositoriesInput{

    MatchingRepositoriesOutput output
    RepositoryDescription repositoryDescription
    DecisionTree tree
    Node currentDecisionLevel
    List<String> decisionStack = new ArrayList<>()


    FindMatchingRepositories(MatchingRepositoriesOutput output, RepositoryDescription repoDescription){
        this.output = output
        this.repositoryDescription = repoDescription

        tree = new DecisionTree()
        currentDecisionLevel = tree.root
    }

    @Override
    def startGuide() {
        def organisms = []

        tree.getFirstDecisionLevel().each {
            organisms << it.data
        }
        output.transferAnswerPossibilities(organisms)

        organisms
    }

    @Override
    def nextAnswerPossibility(String answer) {
        decisionStack.add(answer)

        if(currentDecisionLevel.getChildren().get(0).leaf){
            leafDecision()
        }

        currentDecisionLevel.children.each {
            if(it.data == answer){
                currentDecisionLevel = it
                nodeDecision()
            }
        }
    }

    def nodeDecision(){
        def decisionPossibilities = []

        currentDecisionLevel.children.each {
            decisionPossibilities << it.data
        }

        output.transferDecisionStack(decisionStack)
        output.transferAnswerPossibilities(decisionPossibilities)
    }

    def leafDecision(){

        List matchingRepos = tree.getChildrenData(currentDecisionLevel)

        List<Repository> repositories = repositoryDescription.findRepository(matchingRepos)

        output.transferDecisionStack(decisionStack)
        output.transferRepositoryList(repositories)
    }

}