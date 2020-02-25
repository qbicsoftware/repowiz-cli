package life.qbic.repowiz.application.cli;

import life.qbic.cli.AbstractCommand;
import life.qbic.repowiz.application.cli.subcommands.ListRepositoriesCommand;
import life.qbic.repowiz.application.cli.subcommands.RepositoryGuideCommand;
import life.qbic.repowiz.application.cli.subcommands.SelectRepositoryCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Abstraction of command-line arguments that will be passed to {@link RepowizTool} at construction time.
 */
@Command(
   name="Repowiz",
   description="RepoWiz helps you to find a suitable repository for your data and prepares your submission",
   subcommands = {RepositoryGuideCommand.class, SelectRepositoryCommand.class, ListRepositoriesCommand.class})


public class RepowizCommand extends AbstractCommand {
    @CommandLine.Option(names={"-conf", "--config"}, description="RepoWiz config file")
    String conf;

    @CommandLine.Option(names={"-p", "--projectId"}, required = true, description="Project for which the submission is prepared")
    String projectID;
}

