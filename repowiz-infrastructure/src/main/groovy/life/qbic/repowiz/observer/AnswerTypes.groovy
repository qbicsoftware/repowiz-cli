package life.qbic.repowiz.observer

enum AnswerTypes {

    DECISION("decision"),
    REPOSITORY("repository"),
    UPLOADTYPE("uploadtype"),
    SUBMIT("submit")

    public final String label

    private AnswerTypes(String label) {
        this.label = label
    }
}