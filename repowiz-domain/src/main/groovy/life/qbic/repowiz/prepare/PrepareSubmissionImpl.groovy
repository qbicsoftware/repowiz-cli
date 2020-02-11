package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository

class PrepareSubmissionImpl implements PrepareSubmissionInput, ProjectSpecification{

    MappedMetadata mappedMetadata
    PrepareSubmissionOutput output
    ProjectDetails projectDetails

    PrepareSubmissionImpl(MappedMetadata mappedMetadata, PrepareSubmissionOutput output, ProjectDetails projectDetails){
        this.mappedMetadata = mappedMetadata
        this.output = output
        this.projectDetails = projectDetails
    }

    @Override
    def prepareSubmissionToRepository(Repository repository) {
        //project data
        //transfer to output
        //output.transferProjectFiles()

        return null
    }

    @Override
    def getProject(String projectID) {
        return null
    }

}