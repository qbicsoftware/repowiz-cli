package life.qbic.gui;

import life.qbic.javafx.QBiCApplication;

import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of RepoWiz. Its command-line arguments are contained in instances of {@link SubmissionCommand}.
 */
public class SubmissionApplication extends QBiCApplication<SubmissionCommand> {

    private static final Logger LOG = LogManager.getLogger(SubmissionApplication.class);

    @Override
    public void start(final Stage stage) throws Exception {
        // TODO: do the JavaFX thing
    }
}