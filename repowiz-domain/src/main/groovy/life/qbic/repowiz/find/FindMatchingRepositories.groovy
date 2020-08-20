package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.find.tree.DecisionTree
import life.qbic.repowiz.find.tree.Node
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * This class is responsible for finding a matching repository for a users data
 *
 * This class should be used whenever a matching repository needs to be found. A decision tree is used in order to find a repository.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class FindMatchingRepositories implements FindMatchingRepositoriesInput {

    private static final Logger LOG = LogManager.getLogger(FindMatchingRepositories.class)

    private final MatchingRepositoriesOutput output
    private final RepositoryDescription repositoryDescription

    private DecisionTree tree
    private Node currentDecisionLevel
    private List<String> decisionStack = new ArrayList<>()

    /**
     * Creates the FindMatchingRepositories use case with the output port {@link MatchingRepositoriesOutput} and the {@link RepositoryDescription}
     * @param output defines how the output is handled
     * @param repoDescription offers a description for a repository
     */
    FindMatchingRepositories(MatchingRepositoriesOutput output, RepositoryDescription repoDescription) {
        this.output = output
        this.repositoryDescription = repoDescription

        tree = new DecisionTree()
        currentDecisionLevel = tree.root
    }

    @Override
    void startGuide() {
        LOG.info "Starting the RepoWiz guide ..."
        def organisms = []

        tree.getFirstDecisionLevel().each {
            organisms << it.data
        }

        output.transferAnswerPossibilities(organisms)//todo use observer pattern!
    }

    @Override
    boolean validateDecision(String answer) {
        decisionStack.add(answer)
        output.transferDecisionStack(decisionStack)

        boolean validAnswer = false

        currentDecisionLevel.children.each {
            if (it.data == answer) {
                validAnswer = true
                currentDecisionLevel = it
                if (it.children.get(0).leaf) {
                    leafDecision()
                } else {
                    nodeDecision()
                }
            }
        }
        return validAnswer
    }

    /**
     * If the answer given by the user specifies his experiment data, the corresponding node is not a leave node and this function should be called.
     * It traverses all children of the current node in the decision tree and sets the child node with the respective node content as current node
     */
    void nodeDecision() {
        def decisionPossibilities = []

        currentDecisionLevel.children.each {
            decisionPossibilities << it.data
        }

        String answer = output.transferAnswerPossibilities(decisionPossibilities)
        validateDecision(answer)
    }

    /**
     * If a decision ends in a leaf the next decision will be a decision for a repository.
     * After this decision no further decision can be made
     */
    void leafDecision() {

        List<String> matchingRepos = tree.getChildrenData(currentDecisionLevel)

        LOG.info "Following repositories accept your data"
        LOG.info matchingRepos

        List<Repository> repositories = repositoryDescription.findRepositories(matchingRepos)

        displayUploadRequirements(repositories)

        output.transferRepositoryList(repositories)
    }

    /**
     * Method to display the requirements of repositories to the user
     * @param repositories for which the requirements should be displayed
     */
    void displayUploadRequirements(List<Repository> repositories) {
        repositories.each { repo ->
            output.transferUserInformation("For a successful submission of $repo.repositoryName provide: ")
            repo.uploadRequirements.each {
                output.transferUserInformation(it)
            }
        }
    }

}