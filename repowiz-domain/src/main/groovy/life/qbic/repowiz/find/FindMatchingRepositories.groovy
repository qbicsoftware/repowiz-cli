package life.qbic.repowiz.find


import life.qbic.repowiz.RepositoryDescription

class FindMatchingRepositories implements FindMatchingRepositoriesInput{

    MatchingRepositoriesOutput output
    RepositoryDescription repositoryDescription

    FindMatchingRepositories(MatchingRepositoriesOutput output, RepositoryDescription repoDescription){
        this.output = output
        this.repositoryDescription = repoDescription
    }

    @Override
    def suggestRepos(HashMap<String,String> submissionSpecification) {
        return null
    }
}