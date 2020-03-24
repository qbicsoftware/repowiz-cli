package life.qbic.repowiz.cli

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionHandler
import life.qbic.repowiz.finalise.FinaliseSubmissionImpl
import life.qbic.repowiz.finalise.TargetRepository
import life.qbic.repowiz.find.FindMatchingRepositories
import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.observer.AnswerTypes
import life.qbic.repowiz.prepare.PrepareSubmissionImpl
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput
import life.qbic.repowiz.prepare.openBis.OpenBisSession
import life.qbic.repowiz.prepare.openBis.ProjectSearcher
import life.qbic.repowiz.select.SelectRepository
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.finalise.FinaliseSubmission
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

class SubmissionController implements PropertyChangeListener{

    private static final Logger LOG = LogManager.getLogger(SubmissionController.class)

    String projectID
    ProjectSearcher projectSearch
    RepositoryDescription repoDescription
    TargetRepository repository

    SubmissionPresenter presenter

    //use cases
    FindMatchingRepositoriesInput findRepository
    SelectRepositoryInput selectRepository
    PrepareSubmissionInput prepareSubmission
    FinaliseSubmission finaliseSubmission

    SubmissionController(CommandlineView view, String projectID, String config) {
        this.projectID = projectID
        // set up infrastructure classes
        presenter = new SubmissionPresenter(view)
        repository = new TargetRepository()

        // set up repository database
        repoDescription = new RepositoryDatabaseConnector()

        setupLocalDatabaseConnection(config)
    }

    //method to manage the local database connection (input domain)
    def setupLocalDatabaseConnection(String config) {
        //local database connection
        JsonParser confParser = new JsonParser()
        Map conf = (Map) confParser.parseAsStream(config)
        OpenBisSession session = new OpenBisSession((String) conf.get("user"), (String) conf.get("password"), (String) conf.get("server_url"))

        String sessionToken = session.sessionToken
        IApplicationServerApi v3 = session.v3
        IDataStoreServerApi dss = session.dss

        projectSearch = new ProjectSearcher(v3, dss, sessionToken)
    }


    def initWithSelection(String repo) {
        finaliseSubmission = new FinaliseSubmissionImpl(repository)
        SubmissionHandler finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter)
        finaliseSubmission.addSubmissionHandler(finaliseHandler)

        //SubmissionHandler finaliseHandler = new SubmissionHandler(presenter)

        prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch)
        SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter)

        projectSearch.addProjectSearchOutput(prepareSubmission)

        selectRepository = new SelectRepository(prepareHandler, repoDescription)

        selectRepository.selectRepository(repo.toLowerCase())
    }

    def initWithGuide() {

        finaliseSubmission = new FinaliseSubmissionImpl(repository)
        PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter)
        finaliseSubmission.addSubmissionHandler(finaliseHandler)

        prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch)
        SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter)

        projectSearch.addProjectSearchOutput(prepareSubmission)


        selectRepository = new SelectRepository(prepareHandler)
        SubmissionHandler selectHandler = new SubmissionHandler(selectRepository, presenter)

        findRepository = new FindMatchingRepositories(selectHandler, repoDescription)
        findRepository.startGuide()

    }

    @Override
    void propertyChange(PropertyChangeEvent evt) {
        String userAnswer = (String) evt.getNewValue()
        String answer = evt.getPropertyName()

        switch (answer){
            case AnswerTypes.DECISION.label:
                findRepository.processDesicion(userAnswer)
                break
            case AnswerTypes.REPOSITORY.label:
                selectRepository.processRepository(userAnswer)
                break
            case AnswerTypes.UPLOADTYPE.label:
                prepareSubmission.processUploadType(userAnswer)
                break
            case AnswerTypes.SUBMIT.label:
                boolean verified = false
                if(answer == "yes") verified = true
                finaliseSubmission.processVerificationOfSubmission(verified)
                break
            default:
                LOG.error "could not define answer type!"
        }
    }

}
