package life.qbic.repowiz.cli

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionHandler
import life.qbic.repowiz.find.FindMatchingRepositories
import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.io.XlsxParser
import life.qbic.repowiz.prepare.PrepareSubmissionImpl
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput

import life.qbic.repowiz.prepare.mapping.GeoMapper
import life.qbic.repowiz.prepare.mapping.GeoParser
import life.qbic.repowiz.prepare.mapping.MapInfoInput
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchMapper
import life.qbic.repowiz.select.SelectRepository
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.submit.FinaliseSubmission

class SubmissionController {

    String projectID
    ProjectSearchMapper projectSearch
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
        parser = new XlsxParser()

        //set up mapping
        map = new GeoMapper(parser)

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
        projectSearch = new ProjectSearchMapper(v3,sessionToken)


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
        findRepository.processUserAnswer(decision)
    }

    def transferRepositoryName(String repository){
        selectRepository.processUserAnswer(repository)
    }

    def transferUploadType(String type){
        prepareSubmission.processUserAnswer(type)
    }

}
