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

        output.transferAnswerPossibilities(organisms)//todo use observer pattern!
        //validateDecision(user_answer)
    }

    @Override
    def validateDecision(String answer){
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
        validateDecision(answer)
    }

    def leafDecision(){

        List<String> matchingRepos = tree.getChildrenData(currentDecisionLevel)

        LOG.info "Following repositories accept your data"
        LOG.info matchingRepos

        List<Repository> repositories = repositoryDescription.findRepositories(matchingRepos)

        //display repository standards/requirements for successful upload
        repositories.each {repo ->
            output.transferRepositoryStandard("The standards for a successful submission of $repo.repositoryName are: ")
            repo.uploadRequirements.each {
                output.transferRepositoryStandard(it)
            }
        }

        output.transferRepositoryList(repositories)
    }

}