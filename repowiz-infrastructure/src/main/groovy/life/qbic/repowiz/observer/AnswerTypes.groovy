package life.qbic.repowiz.observer

/**
 * This enum describes different answer types
 *
 * The answer types are specified with controlled vocabulary since each of them will trigger the transfer of the answer to another class
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
enum AnswerTypes {

    DECISION("decision"),
    REPOSITORY("repository"),
    UPLOADTYPE("uploadtype"),
    SUBMIT("submit")

    public final String label

    /**
     * Creates an AnswerType based on a given label
     * @param label describes the answer type as a string
     */
    private AnswerTypes(String label) {
        this.label = label
    }
}