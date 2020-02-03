package life.qbic.repowiz.application.cli;

import life.qbic.cli.QBiCTool;
import life.qbic.repowiz.*;
import life.qbic.repowiz.find.FindMatchingRepositories;
import life.qbic.repowiz.find.RepositoryDatabaseConnector;
import life.qbic.repowiz.select.SelectRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;

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

        CommandlineOutput commandlineOutput = new CommandlineOutput();
        SubmissionPresenter presenter = new SubmissionPresenter(commandlineOutput);

        SubmissionHandler handler = new SubmissionHandler(presenter);

        String db = "/repositories";
        RepositoryDescription repoDescription = new RepositoryDatabaseConnector(db);

        FindMatchingRepositories findRepository = new FindMatchingRepositories(handler,repoDescription);

        //#############
        SubmissionController controller = new SubmissionController(command.conf,findRepository);

        controller.findRepository();

        // transfer conf content about login stuff to the right class
        // command.conf

    }
}