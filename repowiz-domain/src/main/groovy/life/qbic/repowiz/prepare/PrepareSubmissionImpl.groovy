package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository
import life.qbic.repowiz.model.RepoWizProject
import life.qbic.repowiz.model.RepoWizSample
import life.qbic.repowiz.model.SubmissionModel
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchInput
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchOutput
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class PrepareSubmissionImpl implements PrepareSubmissionInput, ProjectSearchOutput{

    private static final Logger LOG = LogManager.getLogger(PrepareSubmissionImpl.class)

    PrepareSubmissionOutput output
    String project
    ProjectSearchInput projectSearch
    private Repository repo
    private String uploadType

    PrepareSubmissionImpl(PrepareSubmissionOutput output, String projectID, ProjectSearchInput projectSearch){
        this.output = output
        this.project = projectID
        this.projectSearch = projectSearch
    }

    //request upload type information and load project data from local database
    @Override
    def prepareSubmissionToRepository(Repository repository) {
        LOG.info "Preparing submission "
        repo = repository

        //ask the user for upload type
        LOG.info "Request upload type "
        output.transferQuestion(repository.uploadTypes)

        //project data
        LOG.info "Fetch Project Data"
        projectSearch.loadProjectInformation(project)
    }

    //PrepareSubmissionInput
    //user answer describing the upload type e.g hts upload special for each repository
    @Override
    def processUploadType(String answer) {
        //display the user answer for better overview
        output.displayAnswer(answer)
        uploadType = answer
    }

    //project search output
    @Override
    def transferProjectData(RepoWizProject project, List samples) {
        if (isValid(samples,repo)){

            SubmissionModel model = new SubmissionModel(project, samples)
            model.setUploadType(uploadType)

            output.validateProject(model, repo)
        }
        else{
            //todo throw exception/warning
            // output.displayAnswer() oder so
        }
    }

    @Override
    def userNotification(String message) {
        output.displayAnswer(message)
    }

    //todo check if project data matches the repository conditions
    static boolean isValid(List<RepoWizSample> samples, Repository repo){
        //todo
        return false
    }


}