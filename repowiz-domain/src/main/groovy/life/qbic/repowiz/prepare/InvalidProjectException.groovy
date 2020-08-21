package life.qbic.repowiz.prepare

/**
 * An exception that is thrown when an invalid project structure was obtained.
 *
 * This class should be used whenever an invalid project structure is obtained.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class InvalidProjectException extends IllegalArgumentException {

    /**
     * Creates a InvalidProjectException
     * @param message describing the error
     */
    InvalidProjectException(String message) {
        super(message)
    }
}
