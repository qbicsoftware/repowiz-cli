package life.qbic.repowiz.application.cli.subcommands;

import life.qbic.cli.AbstractCommand;
import life.qbic.repowiz.application.cli.RepowizCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name="guide",
        description="A subcommand to use the reppository guide of RepoWiz")

public class RepositoryGuideCommand extends AbstractCommand {

    @CommandLine.ParentCommand
            private RepowizCommand parent;

    @CommandLine.Option(names={""})
    boolean guide;
}
