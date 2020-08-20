package life.qbic.repowiz.application.cli.subcommands;

import life.qbic.repowiz.application.cli.RepowizTool;
import picocli.CommandLine;

@CommandLine.Command(
        name = "select",
        description = "A subcommand to directly prepare a submission for a repository")

public class SelectRepositoryCommand implements Runnable {

    @CommandLine.Option(names = {"-h", "--help"}, description = "Prints usage and exists.", usageHelp = true)
    public volatile boolean printHelp;
    @CommandLine.Option(names = {"-c", "--config"}, required = true, description = "RepoWiz config file")
    String conf;
    @CommandLine.Option(names = {"-p", "--projectId"}, required = true, description = "Project for which the submission is prepared")
    String projectID;
    @CommandLine.Option(names = {"-r", "--repository"}, required = true, description = "Repository name")
    String selectedRepository;

    @Override
    public void run() {
        RepowizTool tool = new RepowizTool(projectID, conf);
        tool.executeSelectRepository(selectedRepository);
    }
}
