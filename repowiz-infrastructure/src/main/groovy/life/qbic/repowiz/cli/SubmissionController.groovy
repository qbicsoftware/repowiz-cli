package life.qbic.repowiz.cli

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionHandler
import life.qbic.repowiz.finalise.FinaliseSubmissionImpl
import life.qbic.repowiz.finalise.Loader
import life.qbic.repowiz.finalise.TargetRepository
import life.qbic.repowiz.find.FindMatchingRepositories
import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.observer.AnswerTypes
import life.qbic.repowiz.prepare.PrepareSubmissionImpl
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput
import life.qbic.repowiz.prepare.openBis.OpenBisSession
import life.qbic.repowiz.prepare.projectSearch.ProjectSearcher
import life.qbic.repowiz.select.SelectRepository
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.finalise.FinaliseSubmission

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class SubmissionController implements PropertyChangeListener{

    String projectID
    ProjectSearcher projectSearch
    RepositoryDescription repoDescription
    TargetRepository repository = null

    SubmissionPresenter presenter

    //use cases
    FindMatchingRepositoriesInput findRepository
    SelectRepositoryInput selectRepository
    PrepareSubmissionInput prepareSubmission
    FinaliseSubmission finaliseSubmission

    private static final Logger LOG = LogManager.getLogger(SubmissionController.class)


    SubmissionController(CommandlineView view, String projectID, String config, Loader loader) {
        this.projectID = projectID
        // set up infrastructure classes
        presenter = new SubmissionPresenter(view)

        //set up infrastructure for loading repositories
        repository = new TargetRepository(loader)

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

        PrepareSubmissionOutput handler = new SubmissionHandler(presenter)
        finaliseSubmission = new FinaliseSubmissionImpl(repository,handler)

        SubmissionHandler finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter)

        prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch)
        SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter)

        projectSearch.addProjectSearchOutput(prepareSubmission)

        selectRepository = new SelectRepository(prepareHandler, repoDescription)

        selectRepository.selectRepository(repo.toLowerCase())
    }

    def initWithGuide() {

        PrepareSubmissionOutput handler = new SubmissionHandler(presenter)
        finaliseSubmission = new FinaliseSubmissionImpl(repository,handler)

        PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter)

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
                findRepository.validateDesicion(userAnswer)
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
