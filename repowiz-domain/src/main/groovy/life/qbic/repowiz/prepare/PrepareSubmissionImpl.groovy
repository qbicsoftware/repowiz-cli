package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository
import life.qbic.repowiz.prepare.mapping.MapInfoInput
import life.qbic.repowiz.prepare.mapping.MapInfoOutput

class PrepareSubmissionImpl implements PrepareSubmissionInput, UserAnswer{

    PrepareSubmissionOutput output
    String project
    ProjectSearchService projectSearch
    MapInfoInput mapInfo


    PrepareSubmissionImpl(PrepareSubmissionOutput output, String projectID, ProjectSearchService projectSearch, MapInfoInput mapInfo){
        this.output = output
        this.project = projectID
        this.projectSearch = projectSearch
        this.mapInfo = mapInfo
    }

    @Override
    def prepareSubmissionToRepository(Repository repository) {
        //ask the user for upload type
        output.transferQuestion(repository.uploadTypes)

        //project data
        //projectSearch.getProjectMetadata(project)
        //method like: get project field list for project


        //transfer to output
        //output.transferProjectFiles()
    }

    @Override
    def handleUserAnswer(String answer) {
        output.displayAnswer(answer)
        getRequiredFields(answer)
    }

    def getRequiredFields(String uploadType){
        //what fields are required
        //todo transfer data to the right repository
        //mapInfo soll ein "übermapper" sein der alle anderen mapper beinhalted
        HashMap<String,HashMap> fields = mapInfo.getFields(uploadType) //add (String repositoryName)
        print fields
        print "i received the answer"
    }
}