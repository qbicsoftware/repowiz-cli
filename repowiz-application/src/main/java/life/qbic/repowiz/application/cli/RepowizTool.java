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
import life.qbic.repowiz.prepare.projectSearch.OpenBisMapper;
import life.qbic.repowiz.prepare.projectSearch.OpenBisProjectSearcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of RepoWiz. Its command-line arguments are contained in instances of {@link RepowizCommand}.
 */
public class RepowizTool{

    private static final Logger LOG = LogManager.getLogger(RepowizTool.class);

    private SubmissionController controller;
    private CommandlineView commandlineView = new RepoWizView();


    public RepowizTool(String projectID, String config){
        // set up infrastructure classes
        OpenBisProjectSearcher searcher = setupLocalDatabaseConnection(config);
        List<String> repos = getImplementedRepositoriesAsList();

        controller = new SubmissionController(commandlineView,projectID, searcher, new RepositoryLoaderJava(),repos);

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

            commandlineView.displayInformation("The following Repositories are implemented: ");
            commandlineView.displayInformation(repoNames);

        }catch (Exception e){
            LOG.error("Cannot load the implemented Plugins");
            e.printStackTrace();
        }
    }

    //method to manage the local database connection (input domain)
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

    private OpenBisMapper setupMapper(){
        //load data from file
        String path = "metadataMapping/openbisMapping.json";

        InputStream stream = OpenBisMapper.class.getClassLoader().getResourceAsStream(path);
        JsonParser jsonParser = new JsonParser(stream);

        Map repoWizTerms = jsonParser.parse();

        return new OpenBisMapper(repoWizTerms);
    }

    private static List<String> getImplementedRepositoriesAsList(){
        List<String> repos = new ArrayList<>();
        String fileName = "services/RepositoryJsonFiles.txt";

        try{
            InputStream stream = SubmissionController.class.getClassLoader().getResourceAsStream(fileName);
            assert stream != null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            for (String line; (line = reader.readLine()) != null; ) {
                repos.add(line);
            }

        }catch (IOException io){
           io.printStackTrace();
        }

        return repos;
    }
}