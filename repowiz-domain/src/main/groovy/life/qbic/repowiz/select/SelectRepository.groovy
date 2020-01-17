package life.qbic.repowiz.select

import life.qbic.repowiz.ProjectSpecification
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionSpecificationInput

class SelectRepository implements SubmissionSpecificationInput{

    ProjectSpecification projectSpecification
    RepositoryDescription repositoryDescription
    RepositorySpecificationOutput output

    SelectRepository(RepositorySpecificationOutput output, RepositoryDescription description, ProjectSpecification project){
        projectSpecification = project
        repositoryDescription = description
        this.output = output
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