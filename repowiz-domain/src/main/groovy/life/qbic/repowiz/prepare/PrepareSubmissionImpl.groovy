package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository

class PrepareSubmissionImpl implements PrepareSubmissionInput{

    MappedMetadata mappedMetadata
    PrepareSubmissionOutput output
    String project
    ProjectSearchService projectSearch

    PrepareSubmissionImpl(MappedMetadata mappedMetadata, PrepareSubmissionOutput output, String projectID, ProjectSearchService projectSearch){
        this.mappedMetadata = mappedMetadata
        this.output = output
        this.project = projectID
        this.projectSearch = projectSearch
    }

    @Override
    def prepareSubmissionToRepository(Repository repository) {
        //project data
        //transfer to output
        //output.transferProjectFiles()
        projectSearch.getProjectMetadata(project)

        return null
    }
}