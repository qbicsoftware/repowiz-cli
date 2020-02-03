package life.qbic.repowiz.application.cli;

import life.qbic.cli.QBiCTool;
import life.qbic.repowiz.*;
import life.qbic.repowiz.application.view.RepoWizView;
import life.qbic.repowiz.find.FindMatchingRepositories;
import life.qbic.repowiz.find.MatchingRepositoriesOutput;
import life.qbic.repowiz.find.RepositoryDatabaseConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

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
        super(command);
    }

    @Override
    public void execute() {
        // get the parsed command-line arguments
        final RepowizCommand command = super.getCommand();

        // set up infrastructure classes
        CommandlineView commandlineView = new RepoWizView();
        SubmissionPresenter presenter = new SubmissionPresenter(commandlineView);

        MatchingRepositoriesOutput matchingRepositoriesOutput = new SubmissionHandler(presenter);

        // set up database
        String db = "/repositories";
        RepositoryDescription repoDescription = new RepositoryDatabaseConnector(db);

        //set up first use case
        FindMatchingRepositories findRepository = new FindMatchingRepositories(matchingRepositoriesOutput,repoDescription);

        //#############
        SubmissionController controller = new SubmissionController(command.conf,findRepository);

       // Scanner scan = new Scanner(System.in);
      //  System.out.println("please say something: ");
      //  String out = scan.nextLine();

      //  System.out.println(out);

        if(command.guide){
            //input
            controller.findRepository();
            //output
        }else{
            //controller.chooseRepo(command.selectedRepository)
        }



    }
}