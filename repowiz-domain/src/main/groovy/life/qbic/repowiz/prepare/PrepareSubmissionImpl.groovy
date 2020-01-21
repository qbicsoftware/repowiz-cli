package life.qbic.repowiz.prepare

import life.qbic.repowiz.ProjectDetails
import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryUploadService

class PrepareSubmissionImpl implements PrepareSubmissionInput, ProjectSpecification{

    MappedMetadata mappedMetadata
    PrepareSubmissionOutput output
    ProjectDetails projectDetails
    //RepositoryUploadService uploadService

    PrepareSubmissionImpl(MappedMetadata mappedMetadata, PrepareSubmissionOutput output, ProjectDetails projectDetails){
        this.mappedMetadata = mappedMetadata
        this.output = output
        this.projectDetails = projectDetails
        //this.uploadService = uploadService
    }

    @Override
    def getProject(String projectID) {
        return null
    }

    def isAPISubmission(Repository repo){

    }

    @Override
    def prepareSubmissionToRepository(Repository repository) {
        //get metadata
        //map metadata
        //transfer to output
        return null
    }

}