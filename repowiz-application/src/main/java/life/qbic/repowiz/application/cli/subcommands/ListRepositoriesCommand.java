package life.qbic.repowiz.application.cli.subcommands;

import life.qbic.cli.AbstractCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name="list",
        description="A subcommand to list all implemented repositories")

public class ListRepositoriesCommand extends AbstractCommand {
    boolean list = true;
}
