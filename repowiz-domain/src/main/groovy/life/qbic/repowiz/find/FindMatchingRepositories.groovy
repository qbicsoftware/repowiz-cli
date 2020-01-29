package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.tree.DecisionTree

class FindMatchingRepositories implements FindMatchingRepositoriesInput{

    MatchingRepositoriesOutput output
    RepositoryDescription repositoryDescription

    FindMatchingRepositories(MatchingRepositoriesOutput output, RepositoryDescription repoDescription){
        this.output = output
        this.repositoryDescription = repoDescription
    }

    @Override
    def suggestRepos(HashMap<String,String> submissionSpecification) {
        DecisionTree tree = new DecisionTree()
        List<String> matchingRepos = tree.findRepository(submissionSpecification)

        List<Repository> repositories = repositoryDescription.findRepository(matchingRepos)

        output.transferRepositoryList(repositories)
    }
}