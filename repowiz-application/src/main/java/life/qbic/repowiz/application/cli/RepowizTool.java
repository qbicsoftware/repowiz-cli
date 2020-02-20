package life.qbic.repowiz.application.cli;

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi;
import life.qbic.cli.QBiCTool;
import life.qbic.repowiz.*;
import life.qbic.repowiz.application.view.RepoWizView;
import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.cli.SubmissionPresenter;
import life.qbic.repowiz.find.FindMatchingRepositories;
import life.qbic.repowiz.find.FindMatchingRepositoriesInput;
import life.qbic.repowiz.RepositoryDatabaseConnector;
import life.qbic.repowiz.io.XlsxParser;
import life.qbic.repowiz.prepare.*;
import life.qbic.repowiz.prepare.mapping.GeoMapper;
import life.qbic.repowiz.prepare.mapping.GeoParser;
import life.qbic.repowiz.prepare.mapping.MapInfoInput;
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchMapper;
import life.qbic.repowiz.select.SelectRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of RepoWiz. Its command-line arguments are contained in instances of {@link RepowizCommand}.
 */
public class RepowizTool extends QBiCTool<RepowizCommand> {

    private static final Logger LOG = LogManager.getLogger(RepowizTool.class);

    /**
     * Constructor.
     * 
     * @param command an object that represents the parsed command-line arguments.
     */
    public RepowizTool(final RepowizCommand command) {
        super(command);
    }

    @Override
    public void execute() {
        // get the parsed command-line arguments
        final RepowizCommand command = super.getCommand();

        // set up infrastructure classes
        CommandlineView commandlineView = new RepoWizView();
        SubmissionPresenter presenter = new SubmissionPresenter(commandlineView);

        //parser
        GeoParser parser = new XlsxParser();

        //set up mapping
        MapInfoInput map = new GeoMapper(parser);

        // set up database
        RepositoryDescription repoDescription = new RepositoryDatabaseConnector();

        //local database connection
        //instantiate session and v3 api
        String sessionToken = "";
        IApplicationServerApi v3 = null;
        ProjectSearchMapper projectSearch = new ProjectSearchMapper(v3,sessionToken);

        //

        /*
        def parse(){
            IO.parseJsonFile(new File(propertiesFile))
        }
        */


        if(command.guide){

            //FinaliseSubmissionImpl finaliseSubmission = new FinaliseSubmissionImpl(presenter);
            //PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter);
            PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(presenter);

            PrepareSubmissionImpl prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, command.projectID, projectSearch, map);
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
        else{

            //FinaliseSubmissionImpl finaliseSubmission = new FinaliseSubmissionImpl(presenter);
            //SubmissionHandler finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter);
            SubmissionHandler finaliseHandler = new SubmissionHandler(presenter);

            PrepareSubmissionImpl prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, command.projectID, projectSearch, map);
            SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter);

            UserInputController uic = new UserInputController(prepareSubmission);
            presenter.setControllerUI(uic);

            projectSearch.addProjectSearchOutput(prepareSubmission);

            SelectRepository selectRepository = new SelectRepository(prepareHandler,repoDescription);
            //UserInputController uic2 = new UserInputController(selectRepository);
            //presenter.setControllerUI(uic2);

            selectRepository.selectRepository(command.selectedRepository.toLowerCase());
        }


    }
}