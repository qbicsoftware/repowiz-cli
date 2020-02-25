package life.qbic.repowiz.application.cli;

import life.qbic.cli.QBiCTool;
import life.qbic.repowiz.application.cli.subcommands.RepositoryGuideCommand;
import life.qbic.repowiz.application.view.RepoWizView;
import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.cli.SubmissionController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of RepoWiz. Its command-line arguments are contained in instances of {@link RepowizCommand}.
 */
public class RepowizTool extends QBiCTool<RepowizCommand> {

    private static final Logger LOG = LogManager.getLogger(RepowizTool.class);

    /**
     * Constructor.
     * 
     * @param command an object that represents the parsed command-line arguments.
     */
    public RepowizTool(final RepowizCommand command) {
        super(command); }

    @Override
    public void execute() {
        // get the parsed command-line arguments
        final RepowizCommand command = super.getCommand();

        // set up infrastructure classes
        CommandlineView commandlineView = new RepoWizView();
        SubmissionController controller = new SubmissionController(commandlineView,command.projectID);

        //start point of the application
       /* if(command.guide){
            controller.initGuide();
        }
        else{
            controller.init(command.selectedRepository);
        }*/
    }
}