package life.qbic.repowiz.cli

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionHandler
import life.qbic.repowiz.finalise.FinaliseSubmissionImpl
import life.qbic.repowiz.finalise.SubmissionOutput
import life.qbic.repowiz.finalise.geo.GeoTemplateParser
import life.qbic.repowiz.finalise.mapping.RepositoryPluginHandler
import life.qbic.repowiz.finalise.mapping.SubmissionToPlugin
import life.qbic.repowiz.finalise.parsing.RepositoryInput
import life.qbic.repowiz.finalise.parsing.RepositoryOutput
import life.qbic.repowiz.find.FindMatchingRepositories
import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.prepare.PrepareSubmissionImpl
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput
import life.qbic.repowiz.prepare.openBis.OpenBisSession
import life.qbic.repowiz.prepare.openBis.ProjectSearcher
import life.qbic.repowiz.select.SelectRepository
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.finalise.FinaliseSubmission

class SubmissionController {

    String projectID
    ProjectSearcher projectSearch
    RepositoryDescription repoDescription
    RepositoryPluginHandler repositoryOutput

    SubmissionPresenter presenter

    //use cases
    FindMatchingRepositoriesInput findRepository
    SelectRepositoryInput selectRepository
    PrepareSubmissionInput prepareSubmission
    FinaliseSubmission finaliseSubmission

    SubmissionController(CommandlineView view, String projectID, String config) {
        this.projectID = projectID
        // set up infrastructure classes
        presenter = new SubmissionPresenter(view, this)


        repositoryOutput = new RepositoryPluginHandler(handelRepositoryPlugins())


        // set up repository database
        repoDescription = new RepositoryDatabaseConnector()

        setupLocalDatabaseConnection(config)

    }

    //method to manage all repository plugins (output domain)
    List<RepositoryInput> handelRepositoryPlugins() {

        RepositoryInput geo = new GeoTemplateParser()
        //add clinvar
        //RepositoryInput clinvar = new ClinVarTemplateParser()

        return [geo]
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


    def init(String repo) {
        finaliseSubmission = new FinaliseSubmissionImpl(repositoryOutput)
        SubmissionHandler finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter)
        finaliseSubmission.addSubmissionHandler(finaliseHandler)

        //SubmissionHandler finaliseHandler = new SubmissionHandler(presenter)

        prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch)
        SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter)

        projectSearch.addProjectSearchOutput(prepareSubmission)

        selectRepository = new SelectRepository(prepareHandler, repoDescription)

        selectRepository.selectRepository(repo.toLowerCase())
    }

    def initGuide() {

        finaliseSubmission = new FinaliseSubmissionImpl(repositoryOutput)
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

    def transferDecision(String decision) {
        findRepository.processDesicion(decision)
    }

    def transferRepositoryName(String repository) {
        selectRepository.processRepository(repository)
    }

    def transferUploadType(String type) {
        prepareSubmission.processUploadType(type)
    }

}
