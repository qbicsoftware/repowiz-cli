package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository
import life.qbic.repowiz.prepare.mapping.MapInfoInput
import life.qbic.repowiz.prepare.mapping.MapInfoOutput
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchService

class PrepareSubmissionImpl implements PrepareSubmissionInput, UserAnswer, MapInfoOutput{

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
        mapInfo.addOutput(this)
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
        mapInfo.getFields(uploadType) //add (String repositoryName)
        //todo
        //mapInfo soll ein "übermapper" sein der alle anderen mapper beinhalted
    }

    @Override
    def transferFields(HashMap fields) {
        println "received the field values"
        //todo handle the fields
        return null
    }
}