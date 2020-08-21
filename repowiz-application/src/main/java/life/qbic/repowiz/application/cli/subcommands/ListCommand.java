package life.qbic.repowiz.application.cli.subcommands;

import life.qbic.repowiz.application.cli.RepowizTool;
import picocli.CommandLine;

@CommandLine.Command(
        name = "list",
        description = "A subcommand for listing all implemented repositories in RepoWiz")
public class ListCommand implements Runnable {

    @CommandLine.Option(names = {"-h", "--help"}, description = "Prints usage and exists.", usageHelp = true)
    public volatile boolean printHelp;

    @Override
    public void run() {
        RepowizTool tool = new RepowizTool();
        tool.executeListing();
    }
}
