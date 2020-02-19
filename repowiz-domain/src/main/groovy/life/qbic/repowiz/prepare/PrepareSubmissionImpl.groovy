package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository
import life.qbic.repowiz.prepare.mapping.MapInfoInput
import life.qbic.repowiz.prepare.mapping.MapInfoOutput

class PrepareSubmissionImpl implements PrepareSubmissionInput,UserAnswer{

    PrepareSubmissionOutput output
    String project
    ProjectSearchService projectSearch
    MapInfoInput mapInfo

    PrepareSubmissionImpl(PrepareSubmissionOutput output, String projectID, ProjectSearchService projectSearch,MapInfoInput mapInfo){
        this.output = output
        this.project = projectID
        this.projectSearch = projectSearch
        this.mapInfo = mapInfo
    }

    @Override
    def prepareSubmissionToRepository(Repository repository) {
        //ask for submission type
        //e.g geo --> hts, affymetrix microarray
        output.transferQuestion(repository.uploadTypes)

        //what fields are required
        // --> use mapper based on repository eg geo --> geo mapper
        //HashMap<String,HashMap> fields = mapInfo.getFields(uploadType)
        //answer of fields is transferred with "transferFields" with mapInfoOutput
        //print fields

        //project data
        //projectSearch.getProjectMetadata(project)
        //method like: get project field list for project


        //transfer to output
        //output.transferProjectFiles()
    }

    @Override
    def handleUserAnswer(String answer) {
        output.displayAnswer(answer)
    }
}