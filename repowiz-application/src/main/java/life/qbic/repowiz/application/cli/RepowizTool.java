package life.qbic.repowiz.application.cli;

import life.qbic.cli.QBiCTool;
import life.qbic.repowiz.*;
import life.qbic.repowiz.application.view.RepoWizView;
import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.cli.SubmissionController;
import life.qbic.repowiz.cli.SubmissionPresenter;
import life.qbic.repowiz.find.FindMatchingRepositories;
import life.qbic.repowiz.find.RepositoryDatabaseConnector;
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

        SubmissionHandler handler = new SubmissionHandler(presenter);

        // set up database
        RepositoryDescription repoDescription = new RepositoryDatabaseConnector();
        SelectRepository selectRepository = new SelectRepository(handler,repoDescription);


        if(command.guide){
            //set up first use case
            FindMatchingRepositories findRepository = new FindMatchingRepositories(handler,repoDescription);
            SubmissionController controller = new SubmissionController(command.conf,findRepository);

            controller.findRepository();
            //handler.addRepositoryInput(selectRepository);

        }
        else{
            //System.out.println("You selected "+command.selectedRepository);
            SubmissionController controller = new SubmissionController(command.conf,selectRepository);
            System.out.println(command.selectedRepository);
            controller.chooseRepo(command.selectedRepository);
        }

    }
}