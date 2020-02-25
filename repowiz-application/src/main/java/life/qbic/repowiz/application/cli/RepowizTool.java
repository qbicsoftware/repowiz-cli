package life.qbic.repowiz.application.cli;

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi;
import life.qbic.cli.QBiCTool;
import life.qbic.repowiz.*;
import life.qbic.repowiz.application.view.RepoWizView;
import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.cli.SubmissionController;
import life.qbic.repowiz.cli.SubmissionPresenter;
import life.qbic.repowiz.find.FindMatchingRepositories;
import life.qbic.repowiz.find.FindMatchingRepositoriesInput;
import life.qbic.repowiz.RepositoryDatabaseConnector;
import life.qbic.repowiz.io.XlsxParser;
import life.qbic.repowiz.prepare.*;
import life.qbic.repowiz.prepare.mapping.GeoMapper;
import life.qbic.repowiz.prepare.mapping.GeoParser;
import life.qbic.repowiz.prepare.mapping.MapInfoInput;
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchMapper;
import life.qbic.repowiz.select.SelectRepository;

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

        if(command.guide){
            controller.initGuide();
        }
        else{
            controller.init(command.selectedRepository);
        }
    }
}