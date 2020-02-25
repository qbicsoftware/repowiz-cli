package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository
import life.qbic.repowiz.UserAnswer
import life.qbic.repowiz.prepare.mapping.MapInfoInput
import life.qbic.repowiz.prepare.mapping.MapInfoOutput
import life.qbic.repowiz.prepare.model.RepoWizData
import life.qbic.repowiz.prepare.model.RepoWizExperiment
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchInput
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchOutput

class PrepareSubmissionImpl implements PrepareSubmissionInput, MapInfoOutput, ProjectSearchOutput{

    PrepareSubmissionOutput output
    String project
    ProjectSearchInput projectSearch
    MapInfoInput mapInfo


    PrepareSubmissionImpl(PrepareSubmissionOutput output, String projectID, ProjectSearchInput projectSearch, MapInfoInput mapInfo){
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
    def processUploadType(String answer) {
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

    //project search output
    @Override
    def transferProjectMetadata(List<RepoWizProject> meta) {
        return null
    }

    @Override
    def transferExperimentMetadata(List<RepoWizExperiment> meta) {
        return null
    }

    @Override
    def transferSampleMetadata(List<RepoWizSample> meta) {
        return null
    }

    @Override
    def transferDataForSamples(List<RepoWizData> meta) {
        return null
    }

    @Override
    def userNotification(String message) {
        output.displayAnswer(message)
    }
}