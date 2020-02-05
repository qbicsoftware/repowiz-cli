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

        String user_answer = output.transferAnswerPossibilities(organisms)
        nextAnswerPossibility(user_answer)
    }

    @Override
    def nextAnswerPossibility(String answer) {
        decisionStack.add(answer)
        output.transferDecisionStack(decisionStack)

        currentDecisionLevel.children.each {
            if(it.data == answer){
                currentDecisionLevel = it
                if(it.children.get(0).leaf){
                    leafDecision()
                }else{
                    nodeDecision()
                }
            }
        }

    }

    def nodeDecision(){
        def decisionPossibilities = []

        currentDecisionLevel.children.each {
            decisionPossibilities << it.data
        }

        String answer = output.transferAnswerPossibilities(decisionPossibilities)
        nextAnswerPossibility(answer)
    }

    def leafDecision(){

        List<String> matchingRepos = tree.getChildrenData(currentDecisionLevel)

        //obtain only repositories that are implemented within the system
        //all other repos are not visible to the user
        List<Repository> repositories = repositoryDescription.findRepository(matchingRepos)

        output.transferRepositoryList(repositories)
    }

}