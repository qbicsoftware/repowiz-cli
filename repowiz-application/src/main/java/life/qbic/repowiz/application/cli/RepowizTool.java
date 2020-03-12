package life.qbic.repowiz.application.cli;

import life.qbic.repowiz.application.view.RepoWizView;
import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.cli.SubmissionController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of RepoWiz. Its command-line arguments are contained in instances of {@link RepowizCommand}.
 */
public class RepowizTool{

    private static final Logger LOG = LogManager.getLogger(RepowizTool.class);

    private SubmissionController controller;

    public RepowizTool(String projectID, String config){
        // set up infrastructure classes
        CommandlineView commandlineView = new RepoWizView();
        controller = new SubmissionController(commandlineView,projectID, config);
    }

    public RepowizTool(){
    }

    public void executeFindRepository(){
        controller.initGuide();
    }
    public void executeSelectRepository(String selectedRepository) {
        controller.init(selectedRepository);
    }

    public void executeListing(){
        //todo list all implemented repositories for the user
    }
}