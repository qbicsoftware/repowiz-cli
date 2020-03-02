package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.find.tree.DecisionTree
import life.qbic.repowiz.find.tree.Node
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class FindMatchingRepositories implements FindMatchingRepositoriesInput{

    private static final Logger LOG = LogManager.getLogger(FindMatchingRepositories.class)

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
        LOG.info "Starting the RepoWiz guide ..."
        def organisms = []

        tree.getFirstDecisionLevel().each {
            organisms << it.data
        }

        String user_answer = output.transferAnswerPossibilities(organisms)
        processDesicion(user_answer)
    }

    @Override
    def processDesicion(String answer){
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
        def decisionPossibilities = []

        currentDecisionLevel.children.each {
            decisionPossibilities << it.data
        }

        String answer = output.transferAnswerPossibilities(decisionPossibilities)
        processDesicion(answer)
    }

    def leafDecision(){

        List<String> matchingRepos = tree.getChildrenData(currentDecisionLevel)

        //obtain only repositories that are implemented within the system
        //all other repos are not visible to the user
        List<Repository> repositories = repositoryDescription.findRepository(matchingRepos)

        output.transferRepositoryList(repositories)
    }

}