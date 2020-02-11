package life.qbic.repowiz.application.cli;

import life.qbic.cli.AbstractCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Abstraction of command-line arguments that will be passed to {@link RepowizTool} at construction time.
 */
@Command(
   name="Repowiz",
   description="Command-line utility to determine a suitable repository for a data set and create a submission")

public class RepowizCommand extends AbstractCommand {
    @CommandLine.Option(names={"-conf", "--config"}, description="RepoWiz config file")
    String conf;

    @CommandLine.Option(names={"-g", "--guide"}, description="RepoWiz guide to find suitable repository")
    boolean guide;

    @CommandLine.Option(names={"-s", "--select"}, description="Repository for which an upload should be prepared (skips the guide)")
    String selectedRepository;

    @CommandLine.Option(names={"-p", "--projectId"}, required = true, description="Project for which the submission is prepared")
    String projectID;
}