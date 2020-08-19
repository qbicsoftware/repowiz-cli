package life.qbic.repowiz.prepare

import life.qbic.repowiz.Repository
import life.qbic.repowiz.model.RepoWizProject
import life.qbic.repowiz.model.RepoWizSample
import life.qbic.repowiz.model.SubmissionModel
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchInput
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchOutput
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * This class describes the process of the submission preparation.
 *
 * This class should be used for preparing a submission. It obtains submission data as a {@link SubmissionModel} through the {@link ProjectSearchOutput} interface.
 * This data is then processed and passed on to the {@link PrepareSubmissionOutput}.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class PrepareSubmissionImpl implements PrepareSubmissionInput, ProjectSearchOutput {

    private static final Logger LOG = LogManager.getLogger(PrepareSubmissionImpl.class)

    private final PrepareSubmissionOutput output
    private final String project
    private final ProjectSearchInput projectSearch
    private Repository repo
    private String uploadType

    /**
     * Creates a PrepareSubmissionImpl object
     * @param output defines the target output class defined by the {@link PrepareSubmissionOutput} interface
     * @param projectID is the ID of the selected projected
     * @param projectSearch defines how the project can be searched
     */
    PrepareSubmissionImpl(PrepareSubmissionOutput output, String projectID, ProjectSearchInput projectSearch) {
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
        //todo close openbis session
    }

    //PrepareSubmissionInput
    //user answer describing the upload type e.g hts upload special for each repository
    @Override
    def setUploadType(String answer) {
        //display the user answer for better overview
        output.displayAnswer(answer)
        uploadType = answer
    }

    //project search output
    @Override
    def createSubmissionModel(RepoWizProject project, List samples) throws InvalidProjectException {
        LOG.info "Validate project data"
        if (isValid(samples, repo)) {

            SubmissionModel model = new SubmissionModel(project, samples)
            model.setUploadType(uploadType)

            output.finaliseSubmission(model, repo)
        } else {
            throw new InvalidProjectException("Your project does not fit the selected repository " + repo.repositoryName)
            //todo throw exception/warning
            // output.displayAnswer() ?
        }
    }

    @Override
    def userNotification(String message) {
        output.displayAnswer(message)
    }

    //todo check if project data matches the repository conditions
    /**
     * This method checks if the collected sample data fits the selected repository
     * @param samples with the data of the submission
     * @param repo which was selected by the user
     * @return true if the actual sample data fits the repository requirements
     */
    static boolean isValid(List<RepoWizSample> samples, Repository repo) {
        //todo
        return true
    }


}