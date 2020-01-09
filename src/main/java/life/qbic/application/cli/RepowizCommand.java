package life.qbic.application.cli;

import life.qbic.cli.AbstractCommand;
import picocli.CommandLine.Command;

/**
 * Abstraction of command-line arguments that will be passed to {@link RepowizTool} at construction time.
 */
@Command(
   name="Repowiz",
   description="Command-line utility to...")
public class RepowizCommand extends AbstractCommand {
    // TODO: add your command-line options as members of this class using picocli's annotations, for instance:
    //
    // @Option(names={"-u", "--url"}, description="openBIS server URL.", required=true)
    // String url;
    //
    // using package access level for these members will allow you access them within your main and test classes
    //
    // IMPORTANT: Typically you won't require a fancy constructor, but if you do, you must know that
    //            ToolExecutor requires that all command classes contain a public constructor that takes no arguments.
    //
    //            If you need a custom constructor, make sure to provide a no-arguments public constructor as well.
    //            See: https://docs.oracle.com/javase/tutorial/java/javaOO/constructors.html
}