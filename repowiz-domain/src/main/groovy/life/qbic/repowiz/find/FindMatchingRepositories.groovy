package life.qbic.repowiz.find

import life.qbic.repowiz.DatabaseProjectInformation
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionSpecificationInput

class FindMatchingRepositories implements SubmissionSpecificationInput{

    MatchingRepositoriesOutput output
    DatabaseProjectInformation projectInformation
    RepositoryDescription repositoryDescription

    FindMatchingRepositories(MatchingRepositoriesOutput output, DatabaseProjectInformation projectInfo, RepositoryDescription repoDescription){
        this.output = output
        this.projectInformation = projectInfo
        this.repositoryDescription = repoDescription
    }

    @Override
    def suggestReposForProject(String projectID) {
        return null
    }

    def isProjectIdValid(String projectID){

        false
    }

    @Override
    def getRepositoryInfo(String repository) {
        return null
    }
}