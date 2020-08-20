package life.qbic.repowiz.application.cli;

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi;
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi;
import life.qbic.repowiz.application.spi.RepositoryLoaderJava;
import life.qbic.repowiz.application.view.RepoWizView;
import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.cli.SubmissionController;
import life.qbic.repowiz.io.JsonParser;
import life.qbic.repowiz.observer.UserAnswer;
import life.qbic.repowiz.prepare.openBis.OpenBisSession;
import life.qbic.repowiz.prepare.projectSearch.OpenBisMapper;
import life.qbic.repowiz.prepare.projectSearch.OpenBisProjectSearcher;
import life.qbic.repowiz.spi.TargetRepositoryProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of RepoWiz. Its command-line arguments are contained in instances of {@link RepowizCommand}.
 *
 * This class should be used to access RepoWiz and start the tool
 *
 * @since: 1.0.0
 * @author: Jennifer Bödker
 */
public class RepowizTool {

    private static final Logger LOG = LogManager.getLogger(RepowizTool.class);

    private SubmissionController controller;
    private CommandlineView commandlineView = new RepoWizView();

    /**
     * Sets up the infrastructure of RepoWiz by building the connection to the local database, assembling the controller class and add a change listener to the user answer.
     *
     * @param projectID id of the project that needs to be prepared for an uploaded
     * @param config    configuration with data to connect to the local database
     */
    public RepowizTool(String projectID, String config) {
        OpenBisProjectSearcher searcher = setupLocalDatabaseConnection(config);

        List<String> repos = getImplementedRepositoriesAsList();

        controller = new SubmissionController(commandlineView, projectID, searcher, new RepositoryLoaderJava(), repos);

        UserAnswer answer = new UserAnswer();
        answer.addPropertyChangeListener(controller);

        commandlineView.setUserAnswer(answer);
    }

    public RepowizTool() {
    }

    /**
     * Determines all repository plugins that can be accessed by RepoWiz
     *
     * @return a list of all repository plugins
     */
    private static List<String> getImplementedRepositoriesAsList() {
        List<String> repos = new ArrayList<>();
        String fileName = "services/RepositoryJsonFiles.txt";

        try {
            InputStream stream = SubmissionController.class.getClassLoader().getResourceAsStream(fileName);
            assert stream != null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            for (String line; (line = reader.readLine()) != null; ) {
                repos.add(line);
            }

        } catch (IOException io) {
            io.printStackTrace();
        }

        return repos;
    }

    /**
     * Starts the tool with the use case FindRepository (suggested for inexperienced scientists)
     */
    public void executeFindRepository() {
        controller.initWithGuide();
    }

    /**
     * Starts the tool with the use case SelectRepository (suggested for experienced scientists)
     *
     * @param selectedRepository defines the repository which is selected by the user
     */
    public void executeSelectRepository(String selectedRepository) {
        controller.initWithSelection(selectedRepository);
    }

    //method to manage the local database connection (input domain)

    /**
     * Lists all repositories which are supported by RepoWiz.
     */
    public void executeListing() {
        RepositoryLoaderJava loader = new RepositoryLoaderJava();
        try {
            List<TargetRepositoryProvider> providers = loader.load();

            List<String> repoNames = new ArrayList<>();
            for (TargetRepositoryProvider provider : providers) {
                repoNames.add(provider.getProviderName());
            }

            commandlineView.displayInformation("The following repositories are implemented: ");
            commandlineView.displayInformation(repoNames);

        } catch (Exception e) {
            LOG.error("Cannot load the implemented plugins");
            e.printStackTrace();
        }
    }

    /**
     * Manages the setup of the connection to the local database instance based on the user credentials from the config
     *
     * @param config contains information about user credentials and local db instance
     * @return a searcher object which can be used for searching OpenBis projects OR null
     */
    private OpenBisProjectSearcher setupLocalDatabaseConnection(String config) {
        //local database connection
        try {
            InputStream stream = new FileInputStream(config);
            JsonParser confParser = new JsonParser(stream);

            Map conf = confParser.parse();
            OpenBisSession session = new OpenBisSession((String) conf.get("user"), (String) conf.get("password"), (String) conf.get("server_url"));

            String sessionToken = session.getSessionToken();
            IApplicationServerApi v3 = session.getV3();
            IDataStoreServerApi dss = session.getDss();

            OpenBisMapper mapper = setupMapper();

            String projectSchema = "metadataMapping/RepoWizProject.schema.json";
            String sampleSchema = "metadataMapping/RepoWizSample.schema.json";


            return new OpenBisProjectSearcher(v3, dss, sessionToken, mapper, projectSchema, sampleSchema);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    /**
     * Sets up the OpenBis mapper class which translates OpenBis terms into RepoWiz terms
     *
     * @return a mapper which is capable to translate OpenBis vocabulary
     */
    private OpenBisMapper setupMapper() {
        //load data from file
        String path = "metadataMapping/openbisMapping.json";

        InputStream stream = OpenBisMapper.class.getClassLoader().getResourceAsStream(path);
        JsonParser jsonParser = new JsonParser(stream);

        Map repoWizTerms = jsonParser.parse();

        return new OpenBisMapper(repoWizTerms);
    }
}
