package life.qbic.repowiz.application.cli.subcommands;

import life.qbic.cli.AbstractCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name="select",
        description="A subcommand to directly prepare a submission for a repository")

public class SelectRepositoryCommand extends AbstractCommand {

    @CommandLine.Option(names={"-r", "--repository"}, description="Repository name")
    String selectedRepository;
}
