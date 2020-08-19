package life.qbic.repowiz.application.cli;

import life.qbic.cli.ToolExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

/**
 * Entry point for the RepoWiz application.
 *
 * The purpose of this class is to act as a bridge between the command line and the <i>real</i> implementation of a tool by using a {@link ToolExecutor}.
 *
 * @since: 1.0.0
 * @author: Jennifer Bödker
 *
 */
public class RepowizEntryPoint {

    private static final Logger LOG = LogManager.getLogger(RepowizEntryPoint.class);

    /**
     * Main method.
     *
     * @param args the command-line arguments.
     */
    public static void main(final String[] args) {
        LOG.debug("Starting Repowiz tool ...");

        CommandLine cmd = new CommandLine(new RepowizCommand());
        if (args.length == 0) {
            cmd.usage(System.out);
        } else {
            cmd.setExecutionStrategy(new CommandLine.RunAll());
            cmd.execute(args); //get the arguments from the commandline and give them to RepowizCommand class
        }
    }
}