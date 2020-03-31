package life.qbic.repowiz.application.cli;

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi;
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi;
import life.qbic.repowiz.application.spi.RepositoryLoaderJava;
import life.qbic.repowiz.application.view.RepoWizView;
import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.cli.SubmissionController;

import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider;
import life.qbic.repowiz.io.JsonParser;
import life.qbic.repowiz.observer.UserAnswer;
import life.qbic.repowiz.prepare.openBis.OpenBisSession;
import life.qbic.repowiz.prepare.projectSearch.ProjectSearcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of RepoWiz. Its command-line arguments are contained in instances of {@link RepowizCommand}.
 */
public class RepowizTool{

    private static final Logger LOG = LogManager.getLogger(RepowizTool.class);

    private SubmissionController controller;
    private CommandlineView commandlineView;

    public RepowizTool(String projectID, String config){
        // set up infrastructure classes
        commandlineView = new RepoWizView();
        ProjectSearcher searcher = setupLocalDatabaseConnection(config);

        controller = new SubmissionController(commandlineView,projectID, searcher, new RepositoryLoaderJava());

        UserAnswer answer = new UserAnswer();
        answer.addPropertyChangeListener(controller);

        commandlineView.setUserAnswer(answer);
    }

    public RepowizTool(){
        //add spi provider here!
    }

    public void executeFindRepository(){
        controller.initWithGuide();
    }
    public void executeSelectRepository(String selectedRepository) {
        controller.initWithSelection(selectedRepository);
    }

    public void executeListing(){
        RepositoryLoaderJava loader = new RepositoryLoaderJava();
        try{
            List<TargetRepositoryProvider> providers = loader.load();

            List<String> repoNames = new ArrayList<>();
            for (TargetRepositoryProvider provider:providers) {
                repoNames.add(provider.getProviderName());
            }

            commandlineView.displayInformation("The following Repositories are implemented");
            commandlineView.displayInformation(repoNames);

        }catch (Exception e){
            LOG.error("Cannot load the implemented Plugins");
            e.printStackTrace();
        }
    }

    //method to manage the local database connection (input domain)
    ProjectSearcher setupLocalDatabaseConnection(String config) {
        //local database connection
        JsonParser confParser = new JsonParser();
        try {
            Map conf = (Map) confParser.parseAsStream(config);
            OpenBisSession session = new OpenBisSession((String) conf.get("user"), (String) conf.get("password"), (String) conf.get("server_url"));

            String sessionToken = session.getSessionToken();
            IApplicationServerApi v3 = session.getV3();
            IDataStoreServerApi dss = session.getDss();

            return new ProjectSearcher(v3, dss, sessionToken);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}