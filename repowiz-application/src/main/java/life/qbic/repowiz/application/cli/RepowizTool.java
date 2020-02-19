package life.qbic.repowiz.application.cli;

import life.qbic.cli.QBiCTool;
import life.qbic.repowiz.*;
import life.qbic.repowiz.application.view.RepoWizView;
import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.cli.SubmissionPresenter;
import life.qbic.repowiz.find.FindMatchingRepositories;
import life.qbic.repowiz.find.FindMatchingRepositoriesInput;
import life.qbic.repowiz.find.MatchingRepositoriesOutput;
import life.qbic.repowiz.RepositoryDatabaseConnector;
import life.qbic.repowiz.io.XlsxParser;
import life.qbic.repowiz.prepare.*;
import life.qbic.repowiz.prepare.mapping.GeoMapper;
import life.qbic.repowiz.prepare.mapping.GeoParser;
import life.qbic.repowiz.prepare.mapping.MapInfoInput;
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchService;
import life.qbic.repowiz.select.SelectRepository;

import life.qbic.repowiz.select.SelectRepositoryInput;
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
        ProjectSearchService projectSearchService = new ProjectSearchConnector();

        //

        /*
        def parse(){
            IO.parseJsonFile(new File(propertiesFile))
        }
        */


        if(command.guide){
            //SubmissionController controller = new SubmissionController(command.conf,findRepository);
            //controller.findRepository();
            //handler.addRepositoryInput(selectRepository);

            //FinaliseSubmissionImpl finaliseSubmission = new FinaliseSubmissionImpl(presenter);
            //PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter);
            PrepareSubmissionOutput finaliseHandler = new SubmissionHandler(presenter);

            PrepareSubmissionImpl prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, command.projectID, projectSearchService, map);
            UserInputController uic = new UserInputController(prepareSubmission);
            SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter);
            presenter.setControllerUI(uic);

            SelectRepository selectRepositoryInput = new SelectRepository(prepareHandler);
            //UserInputController uic2 = new UserInputController(selectRepositoryInput);
            SubmissionHandler selectHandler = new SubmissionHandler(selectRepositoryInput, presenter);
            //presenter.setControllerUI(uic2);


            FindMatchingRepositoriesInput findRepository = new FindMatchingRepositories(selectHandler,repoDescription);
            findRepository.startGuide();

        }
        else{
            //System.out.println("You selected "+command.selectedRepository);
            //SubmissionController controller = new SubmissionController(command.conf,selectRepository);
            //System.out.println(command.selectedRepository);
            //controller.chooseRepo(command.selectedRepository);

            //FinaliseSubmissionImpl finaliseSubmission = new FinaliseSubmissionImpl(presenter);
            //SubmissionHandler finaliseHandler = new SubmissionHandler(finaliseSubmission, presenter);
            SubmissionHandler finaliseHandler = new SubmissionHandler(presenter);

            PrepareSubmissionImpl prepareSubmission = new PrepareSubmissionImpl(finaliseHandler, command.projectID, projectSearchService, map);
            UserInputController uic = new UserInputController(prepareSubmission);
            SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter);
            presenter.setControllerUI(uic);

            SelectRepository selectRepository = new SelectRepository(prepareHandler,repoDescription);
            //UserInputController uic2 = new UserInputController(selectRepository);
            //presenter.setControllerUI(uic2);

            selectRepository.selectRepository(command.selectedRepository.toLowerCase());
        }


    }
}