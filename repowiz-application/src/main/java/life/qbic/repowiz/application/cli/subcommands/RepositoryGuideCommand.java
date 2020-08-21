package life.qbic.repowiz.application.cli.subcommands;

import life.qbic.repowiz.application.cli.RepowizTool;
import picocli.CommandLine;

@CommandLine.Command(
        name = "guide",
        description = "A subcommand to use the repository guide of RepoWiz")

public class RepositoryGuideCommand implements Runnable {

    @CommandLine.Option(names = {"-h", "--help"}, description = "Prints usage and exists.", usageHelp = true)
    public volatile boolean printHelp;
    @CommandLine.Option(names = {"-c", "--config"}, required = true, description = "RepoWiz config file")
    String conf;
    @CommandLine.Option(names = {"-p", "--projectId"}, required = true, description = "Project for which the submission is prepared")
    String projectID;

    @Override
    public void run() {
        RepowizTool tool = new RepowizTool(projectID, conf);
        tool.executeFindRepository();
    }
}
