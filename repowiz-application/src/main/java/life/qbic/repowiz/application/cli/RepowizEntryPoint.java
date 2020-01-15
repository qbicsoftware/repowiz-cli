package life.qbic.repowiz.application.cli;

import life.qbic.cli.ToolExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Entry point for the RepoWiz application.
 * 
 * The purpose of this class is to act as a bridge between the command line and the <i>real</i> implementation of a tool by using a {@link ToolExecutor}.
 */
public class RepowizEntryPoint {

    private static final Logger LOG = LogManager.getLogger(RepowizEntryPoint.class);

    /**
     * Main method.
     * 
     * @param args the command-line arguments.
     */
    public static void main(final String[] args) {
        LOG.debug("Starting Repowiz tool");
        final ToolExecutor executor = new ToolExecutor();
        executor.invoke(RepowizTool.class, RepowizCommand.class, args);
    }
}