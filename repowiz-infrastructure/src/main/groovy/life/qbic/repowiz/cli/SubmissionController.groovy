package life.qbic.repowiz.cli

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import life.qbic.repowiz.RepositoryDatabaseConnector
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.SubmissionHandler
import life.qbic.repowiz.find.FindMatchingRepositories
import life.qbic.repowiz.find.FindMatchingRepositoriesInput
import life.qbic.repowiz.io.XlsxParser
import life.qbic.repowiz.prepare.PrepareSubmissionImpl
import life.qbic.repowiz.prepare.PrepareSubmissionOutput
import life.qbic.repowiz.prepare.UserInputController
import life.qbic.repowiz.prepare.mapping.GeoMapper
import life.qbic.repowiz.prepare.mapping.GeoParser
import life.qbic.repowiz.prepare.mapping.MapInfoInput
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchMapper
import life.qbic.repowiz.select.SelectRepository

class SubmissionController {

    String projectID
    ProjectSearchMapper projectSearch
    RepositoryDescription repoDescription
    MapInfoInput map
    GeoParser parser
    SubmissionPresenter presenter

    SubmissionController(CommandlineView view, String projectID){
        this.projectID = projectID
        // set up infrastructure classes
        presenter = new SubmissionPresenter(view);

        //parser
        parser = new XlsxParser();

        //set up mapping
        map = new GeoMapper(parser);

        // set up database
        repoDescription = new RepositoryDatabaseConnector();

        //local database connection
        //instantiate session and v3 api from config file (given by command?)
        /*
        def parse(){
            IO.parseJsonFile(new File(propertiesFile))
        }
        */

        String sessionToken = "";
        IApplicationServerApi v3 = null;
        projectSearch = new ProjectSearchMapper(v3,sessionToken);


    }


    def init(String repo){

            //FinaliseSubmissionImpl finaliseSubmission = new FinaliseSubmissionImpl(presenter);
            //SubmissionHandler finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter);
            SubmissionHandler finaliseHandler = new SubmissionHandler(presenter);

            PrepareSubmissionImpl prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch, map);
            SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter);

            UserInputController uic = new UserInputController(prepareSubmission);
            presenter.setControllerUI(uic);

            projectSearch.addProjectSearchOutput(prepareSubmission);

            SelectRepository selectRepository = new SelectRepository(prepareHandler,repoDescription);
            //UserInputController uic2 = new UserInputController(selectRepository);
            //presenter.setControllerUI(uic2);

            selectRepository.selectRepository(repo.toLowerCase());
    }

    def initGuide(){

            //FinaliseSubmissionImpl finaliseSubmission = new FinaliseSubmissionImpl(presenter);
            //PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter);
            PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(presenter);

            PrepareSubmissionImpl prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, projectID, projectSearch, map);
            SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter);

            UserInputController uic = new UserInputController(prepareSubmission);
            presenter.setControllerUI(uic);

            projectSearch.addProjectSearchOutput(prepareSubmission);


            SelectRepository selectRepositoryInput = new SelectRepository(prepareHandler);
            //UserInputController uic2 = new UserInputController(selectRepositoryInput);
            SubmissionHandler selectHandler = new SubmissionHandler(selectRepositoryInput, presenter);
            //presenter.setControllerUI(uic2);

            FindMatchingRepositoriesInput findRepository = new FindMatchingRepositories(selectHandler,repoDescription);
            findRepository.startGuide();

    }



}
