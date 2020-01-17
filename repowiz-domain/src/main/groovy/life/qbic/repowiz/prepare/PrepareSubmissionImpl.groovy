package life.qbic.repowiz.prepare

import life.qbic.repowiz.RepositoryUploadService

class PrepareSubmissionImpl implements PrepareSubmissionInput{

    MappedMetadata mappedMetadata
    ProjectSubmissionOutput output
    RepositoryUploadService uploadService

    PrepareSubmissionImpl(MappedMetadata mappedMetadata,ProjectSubmissionOutput output,RepositoryUploadService uploadService){
        this.mappedMetadata = mappedMetadata
        this.output = output
        this.uploadService = uploadService
    }

    @Override
    def prepareSubmissionForProject(String projectCode) {
        return null
    }
}