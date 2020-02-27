package life.qbic.repowiz.application.cli.subcommands;

import life.qbic.cli.AbstractCommand;
import life.qbic.repowiz.application.cli.RepowizCommand;
import life.qbic.repowiz.application.cli.RepowizTool;
import picocli.CommandLine;

@CommandLine.Command(
        name="guide",
        description="A subcommand to use the repository guide of RepoWiz")

public class RepositoryGuideCommand implements Runnable{

    @CommandLine.Option(names={"-conf", "--config"}, required = true, description="RepoWiz config file")
    String conf;

    @CommandLine.Option(names={"-p", "--projectId"}, required = true, description="Project for which the submission is prepared")
    String projectID;

    @CommandLine.Option(names = {"-h", "--help"}, description = "Prints usage and exists.", usageHelp = true)
    public volatile boolean printHelp;

    @Override
    public void run() {
        RepowizTool tool = new RepowizTool(projectID);
        tool.executeFindRepository();
    }
}
