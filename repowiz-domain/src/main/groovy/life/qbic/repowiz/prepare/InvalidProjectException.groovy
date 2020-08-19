package life.qbic.repowiz.prepare

/**
 * Describes an {@link IllegalArgumentException} that can be thrown
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
