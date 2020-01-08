package life.qbic.gui;

import life.qbic.javafx.JavaFXExecutor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Entry point for the RepoWiz application.
 * 
 * The purpose of this class is to act as a bridge between the command line and the <i>real</i> implementation of a tool by using a {@link JavaFXExecutor}.
 */
public class SubmissionEntryPoint {

    private static final Logger LOG = LogManager.getLogger(SubmissionEntryPoint.class);

    /**
     * Main method.
     * 
     * @param args the command-line arguments.
     */
    public static void main(final String[] args) {
        LOG.debug("Starting Submission Application");
        final JavaFXExecutor executor = new JavaFXExecutor();
        executor.invokeAsJavaFX(SubmissionApplication.class, SubmissionCommand.class, args);
    }
}