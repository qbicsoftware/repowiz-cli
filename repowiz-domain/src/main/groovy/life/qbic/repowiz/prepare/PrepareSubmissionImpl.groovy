package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository
import life.qbic.repowiz.prepare.mapping.MapInfoInput
import life.qbic.repowiz.prepare.mapping.MapInfoOutput

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchInput
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchOutput
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class PrepareSubmissionImpl implements PrepareSubmissionInput, MapInfoOutput, ProjectSearchOutput{

    private static final Logger LOG = LogManager.getLogger(PrepareSubmissionImpl.class);

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
        LOG.info "Preparing submission "
        mapInfo.addOutput(this)
        LOG.info "Request upload type "
        output.transferQuestion(repository.uploadTypes)

        //project data
        LOG.info "Fetch Project Data"
        projectSearch.loadProjectInformation(project)
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
        LOG.debug "Received the field values from template"
        LOG.debug fields
        //todo handle the fields
        return null
    }

    //project search output
    @Override
    def transferProjectMetadata(RepoWizProject meta) {
        return null
    }

    @Override
    def userNotification(String message) {
        output.displayAnswer(message)
    }
}