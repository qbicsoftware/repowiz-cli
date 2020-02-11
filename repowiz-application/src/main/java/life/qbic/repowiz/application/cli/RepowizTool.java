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
import life.qbic.repowiz.prepare.*;
import life.qbic.repowiz.select.SelectRepository;

import life.qbic.repowiz.select.SelectRepositoryInput;
import life.qbic.repowiz.select.SelectRepositoryOutput;
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


        // set up database
        RepositoryDescription repoDescription = new RepositoryDatabaseConnector();

        //set up mapping
        MappedMetadata mappedMetadata = new OpenBisMapper();

        //local database connection
        //instantiate session and v3 api
        ProjectDetails projectDetails = new OpenBisConnector();


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

            PrepareSubmissionInput prepareSubmission = new PrepareSubmissionImpl(mappedMetadata, finaliseHandler, command.projectID, projectDetails);
            SelectRepositoryOutput prepareHandler = new SubmissionHandler(prepareSubmission, presenter);

            SelectRepositoryInput selectRepositoryInput = new SelectRepository(prepareHandler);
            MatchingRepositoriesOutput selectHandler = new SubmissionHandler(selectRepositoryInput, presenter);

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

            PrepareSubmissionInput prepareSubmission = new PrepareSubmissionImpl(mappedMetadata, finaliseHandler, command.projectID, projectDetails);
            SubmissionHandler prepareHandler = new SubmissionHandler(prepareSubmission, presenter);

            SelectRepository selectRepository = new SelectRepository(prepareHandler,repoDescription);

            selectRepository.selectRepository(command.selectedRepository.toLowerCase());
        }

    }
}