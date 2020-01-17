package life.qbic.repowiz.select

import life.qbic.repowiz.DatabaseProjectInformation
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionSpecificationInput

class SelectRepository implements SubmissionSpecificationInput{

    DatabaseProjectInformation projectSpecification
    RepositoryDescription repositoryDescription
    RepositorySpecificationOutput output

    SelectRepository(RepositorySpecificationOutput output, RepositoryDescription description, DatabaseProjectInformation project){
        projectSpecification = project
        repositoryDescription = description
        this.output = output
    }

    @Override
    def suggestReposForProject(String projectCode) {
        return null
    }

    @Override
    def getRepositoryInfo(String repository) {
        return null
    }

    def repoAcceptsProjectData(List<String> suggestedRepos, String selectedRepo){ //check if chosen repository accepts the data of the project

    }
}