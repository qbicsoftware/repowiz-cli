package life.qbic.repowiz.cli


import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionHandler
import life.qbic.repowiz.finalise.FinaliseSubmissionImpl
import life.qbic.repowiz.finalise.Loader
import life.qbic.repowiz.finalise.TargetRepository
import life.qbic.repowiz.find.FindMatchingRepositories
import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.observer.AnswerTypes
import life.qbic.repowiz.prepare.PrepareSubmissionImpl
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput
import life.qbic.repowiz.prepare.projectSearch.OpenBisProjectSearcher
import life.qbic.repowiz.select.SelectRepository
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.finalise.FinaliseSubmission

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class SubmissionController implements PropertyChangeListener{

    String projectID
    OpenBisProjectSearcher projectSearch
    RepositoryDescription repoDescription
    TargetRepository repository = null

    SubmissionPresenter presenter

    //use cases
    FindMatchingRepositoriesInput findRepository
    SelectRepositoryInput selectRepository
    PrepareSubmissionInput prepareSubmission
    FinaliseSubmission finaliseSubmission

    private static final Logger LOG = LogManager.getLogger(SubmissionController.class)


    SubmissionController(CommandlineView view, String projectID, OpenBisProjectSearcher searcher, Loader loader, List<String> repoFileNames) {
        this.projectID = projectID
        // set up infrastructure classes
        presenter = new SubmissionPresenter(view)

        //define local database
        projectSearch = searcher

        //set up infrastructure for loading repositories
        repository = new TargetRepository(loader)

        // set up repository database
        repoDescription = new RepositoryDatabaseConnector("repositories","repositories/repository.schema.json",repoFileNames)
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

        selectRepository = new SelectRepository(prepareHandler,repoDescription)
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
                if(userAnswer == "yes") verified = true
                finaliseSubmission.processVerificationOfSubmission(verified)
                break
            default:
                LOG.error "could not define answer type!"
        }
    }

}
