package life.qbic.repowiz.find

import life.qbic.repowiz.ProjectSpecification
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionSpecificationInput

class FindMatchingRepositories implements SubmissionSpecificationInput{

    MatchingRepositoriesOutput output
    ProjectSpecification projectSpecification
    RepositoryDescription repositoryDescription

    FindMatchingRepositories(MatchingRepositoriesOutput output, ProjectSpecification projectSpec, RepositoryDescription repoDescription){
        this.output = output
        this.projectSpecification = projectSpec
        this.repositoryDescription = repoDescription
    }

    @Override
    def projectSpecification(String projectCode) {
        return null
    }

    @Override
    def repositorySpecification(String repository) {
        return null
    }
}