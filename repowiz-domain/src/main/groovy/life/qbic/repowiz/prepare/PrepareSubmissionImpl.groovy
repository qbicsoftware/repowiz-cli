package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository
import life.qbic.repowiz.prepare.mapping.MapInfoInput

class PrepareSubmissionImpl implements PrepareSubmissionInput{

    PrepareSubmissionOutput output
    String project
    ProjectSearchService projectSearch

    PrepareSubmissionImpl(PrepareSubmissionOutput output, String projectID, ProjectSearchService projectSearch){
        this.output = output
        this.project = projectID
        this.projectSearch = projectSearch
    }

    @Override
    def prepareSubmissionToRepository(Repository repository) {
        //ask for submission type
        //e.g geo --> hts, affymetrix microarray
        //what fields are required
        // --> use mapper based on repository eg geo --> geo mapper
        //project data
        projectSearch.getProjectMetadata(project)
        //transfer to output
        //output.transferProjectFiles()

        return null
    }
}