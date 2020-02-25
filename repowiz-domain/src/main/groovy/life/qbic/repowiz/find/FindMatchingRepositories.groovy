package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.find.tree.DecisionTree
import life.qbic.repowiz.find.tree.Node
import life.qbic.repowiz.prepare.UserAnswer

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
        def organisms = new HashMap()
        int counter = 1


        tree.getFirstDecisionLevel().each {
            organisms.put(counter, it.data)
            counter ++
        }

        String user_answer = output.transferAnswerPossibilities(organisms)
        nextAnswerPossibility(user_answer)
    }

    @Override
    def nextAnswerPossibility(String answer){
        decisionStack.add(answer)
        output.transferDecisionStack(decisionStack)
        boolean validAnswer = false

        currentDecisionLevel.children.each {
            if(it.data == answer){
                validAnswer = true
                currentDecisionLevel = it
                if(it.children.get(0).leaf){
                    leafDecision()
                }else{
                    nodeDecision()
                }
            }
        }
        validAnswer
    }

    def nodeDecision(){
        def decisionPossibilities = new HashMap()
        int counter = 1

        currentDecisionLevel.children.each {
            decisionPossibilities.put(counter,it.data)
            counter ++
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