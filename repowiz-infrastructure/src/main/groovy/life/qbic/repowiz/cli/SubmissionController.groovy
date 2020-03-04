package life.qbic.repowiz.cli

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionHandler
import life.qbic.repowiz.find.FindMatchingRepositories
import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.io.XlsxParser
import life.qbic.repowiz.prepare.PrepareSubmissionImpl
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput

import life.qbic.repowiz.prepare.mapping.GeoSubmission
import life.qbic.repowiz.prepare.parsing.GeoParserInput
import life.qbic.repowiz.prepare.mapping.MapInfoInput
import life.qbic.repowiz.prepare.projectSearch.geo.GeoParser
import life.qbic.repowiz.prepare.projectSearch.openBis.ProjectSearcher
import life.qbic.repowiz.select.SelectRepository
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.submit.FinaliseSubmission

class SubmissionController {

    String projectID
    ProjectSearcher projectSearch
    RepositoryDescription repoDescription
    MapInfoInput map
    GeoParser parser

    SubmissionPresenter presenter

    //use cases
    FindMatchingRepositoriesInput findRepository
    SelectRepositoryInput selectRepository
    PrepareSubmissionInput prepareSubmission
    FinaliseSubmission finaliseSubmission

    SubmissionController(CommandlineView view, String projectID){
        this.projectID = projectID
        // set up infrastructure classes
        presenter = new SubmissionPresenter(view,this)

        //parser
        parser = new GeoParser(["METADATA TEMPLATE"])

        //set up mapping
        map = new GeoSubmission(parser)

        // set up database
        repoDescription = new RepositoryDatabaseConnector()

        //local database connection
        //instantiate session and v3 api from config file (given by command?)
        /*
        def parse(){
            IO.parseJsonFile(new File(propertiesFile))
        }
        */

        String sessionToken = ""
        IApplicationServerApi v3 = null
        IDataStoreServerApi dss = null
        projectSearch = new ProjectSearcher(v3,dss,sessionToken)


    }


    def init(String repo){

            //finaliseSubmission = new FinaliseSubmissionImpl(presenter);
            //SubmissionHandler finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter);
            SubmissionHandler finaliseHandler = new SubmissionHandler(presenter)

            prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch, map)
            SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter)

            projectSearch.addProjectSearchOutput(prepareSubmission)

            selectRepository = new SelectRepository(prepareHandler,repoDescription)

            selectRepository.selectRepository(repo.toLowerCase())
    }

    def initGuide(){

            //finaliseSubmission = new FinaliseSubmissionImpl(presenter)
            //PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter)
            PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(presenter)

            prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch, map)
            SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter)

            projectSearch.addProjectSearchOutput(prepareSubmission)


            selectRepository = new SelectRepository(prepareHandler)
            SubmissionHandler selectHandler = new SubmissionHandler(selectRepository, presenter)

            findRepository = new FindMatchingRepositories(selectHandler,repoDescription)
            findRepository.startGuide()

    }

    def transferDecision(String decision){
        findRepository.processDesicion(decision)
    }

    def transferRepositoryName(String repository){
        selectRepository.processRepository(repository)
    }

    def transferUploadType(String type){
        prepareSubmission.processUploadType(type)
    }

}
