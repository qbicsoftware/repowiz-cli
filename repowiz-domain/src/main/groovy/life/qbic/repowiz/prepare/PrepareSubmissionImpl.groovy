package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository

class PrepareSubmissionImpl implements PrepareSubmissionInput{

    MappedMetadata mappedMetadata
    PrepareSubmissionOutput output
    String project
    ProjectDetails projectDetails

    PrepareSubmissionImpl(MappedMetadata mappedMetadata, PrepareSubmissionOutput output, String projectID, ProjectDetails projectDetails){
        this.mappedMetadata = mappedMetadata
        this.output = output
        this.project = projectID
        this.projectDetails = projectDetails
    }

    @Override
    def prepareSubmissionToRepository(Repository repository) {
        //project data
        //transfer to output
        //output.transferProjectFiles()
        projectDetails.getProjectMetadata(project)

        return null
    }
}