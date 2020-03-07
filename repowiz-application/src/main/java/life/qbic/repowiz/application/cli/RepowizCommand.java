package life.qbic.repowiz.application.cli;

import life.qbic.repowiz.application.cli.subcommands.RepositoryGuideCommand;
import life.qbic.repowiz.application.cli.subcommands.SelectRepositoryCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Command(
   name="Repowiz",
   description="RepoWiz helps you to find a suitable repository for your data and prepares your submission",
   subcommands = {RepositoryGuideCommand.class, SelectRepositoryCommand.class})

public class RepowizCommand implements Runnable{

    private static final Logger LOG = LogManager.getLogger(RepowizCommand.class);

    @CommandLine.Option(names={"-l", "--list"}, description="Lists all implemented repositories of RepoWiz")
    public volatile String projectID;

    @CommandLine.Option(names = {"-h", "--help"}, description = "Prints usage and exists.", usageHelp = true)
    public volatile boolean printHelp;

    @Override
    public void run() {
        LOG.warn("Please start the tool by one of its subcommands");
    }

}

