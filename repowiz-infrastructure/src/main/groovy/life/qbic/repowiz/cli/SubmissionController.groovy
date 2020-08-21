package life.qbic.repowiz.cli

import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionHandler
import life.qbic.repowiz.finalise.FinaliseSubmission
import life.qbic.repowiz.finalise.FinaliseSubmissionImpl
import life.qbic.repowiz.find.FindMatchingRepositories
import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.observer.AnswerTypes
import life.qbic.repowiz.prepare.PrepareSubmissionImpl
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput
import life.qbic.repowiz.prepare.projectSearch.OpenBisProjectSearcher
import life.qbic.repowiz.select.SelectRepository
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.spi.Loader
import life.qbic.repowiz.spi.TargetRepository
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

/**
 * This class serves as a controller for the flow of the program
 *
 * This class should be used to control how data flows between the different use cases and how classes interact
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class SubmissionController implements PropertyChangeListener {

    private final String projectID
    private final OpenBisProjectSearcher projectSearch
    private final RepositoryDescription repoDescription
    private TargetRepository repository = null

    private final SubmissionPresenter presenter

    //use cases
    private FindMatchingRepositoriesInput findRepository
    private SelectRepositoryInput selectRepository
    private PrepareSubmissionInput prepareSubmission
    private FinaliseSubmission finaliseSubmission

    private static final Logger LOG = LogManager.getLogger(SubmissionController.class)

    /**
     * Creates a SubmissionController which controls the view, the project identifier, the connection to the database, a loader and a list of repository names
     * @param view defines how data is displayed to the user
     * @param projectID defines the users project
     * @param searcher allows to search for projects in openbis
     * @param loader allows to load repositories
     * @param repoFileNames is a list of repository names
     */
    SubmissionController(CommandlineView view, String projectID, OpenBisProjectSearcher searcher, Loader loader, List<String> repoFileNames) {
        this.projectID = projectID
        // set up infrastructure classes
        presenter = new SubmissionPresenter(view)

        //define local database
        projectSearch = searcher

        //set up infrastructure for loading repositories
        repository = new TargetRepository(loader)

        // set up repository database
        repoDescription = new RepositoryDatabaseConnector(repository)
    }

    /**
     * Starts RepoWiz with the {@link SelectRepository} use case, where a repository is already defined. A submission
     * for this repo is created.
     * @param repo that has been specified by the user
     */
    void initWithSelection(String repo) {

        PrepareSubmissionOutput handler = new SubmissionHandler(presenter)
        finaliseSubmission = new FinaliseSubmissionImpl(repository, handler)

        SubmissionHandler finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter)

        prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch)
        SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter)

        projectSearch.addProjectSearchOutput(prepareSubmission)

        selectRepository = new SelectRepository(prepareHandler, repoDescription)

        selectRepository.selectRepository(repo.toLowerCase())
    }

    /**
     * Starts RepoWiz with the {@link FindMatchingRepositories} use case. It helps the user to find the respective repository
     * before the submission is created
     */
    void initWithGuide() {

        PrepareSubmissionOutput handler = new SubmissionHandler(presenter)
        finaliseSubmission = new FinaliseSubmissionImpl(repository, handler)

        PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter)

        prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch)
        SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter)

        projectSearch.addProjectSearchOutput(prepareSubmission)

        selectRepository = new SelectRepository(prepareHandler, repoDescription)
        SubmissionHandler selectHandler = new SubmissionHandler(selectRepository, presenter)

        findRepository = new FindMatchingRepositories(selectHandler, repoDescription)
        findRepository.startGuide()
    }

    @Override
    void propertyChange(PropertyChangeEvent evt) {
        String userAnswer = (String) evt.getNewValue()
        String answer = evt.getPropertyName()

        switch (answer) {
            case AnswerTypes.DECISION.label:
                findRepository.validateDecision(userAnswer)
                break
            case AnswerTypes.REPOSITORY.label:
                selectRepository.validateSelectedRepository(userAnswer)
                break
            case AnswerTypes.UPLOADTYPE.label:
                prepareSubmission.setUploadType(userAnswer)
                break
            case AnswerTypes.SUBMIT.label:
                boolean verified = false
                if (userAnswer == "yes") verified = true
                finaliseSubmission.processVerificationOfSubmission(verified)
                break
            default:
                LOG.error "could not define answer type!"
        }
    }

}
