package life.qbic.repowiz.application.cli;

import life.qbic.cli.QBiCTool;
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

        // TODO: do something useful with the obtained command.
        //
        
    }

    // TODO: override the shutdown() method if you are implementing a daemon and want to take advantage of a shutdown hook for clean-up tasks
}