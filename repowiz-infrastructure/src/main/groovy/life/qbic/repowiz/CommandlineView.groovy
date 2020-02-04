package life.qbic.repowiz

interface CommandlineView {
    //delete??

    //handle input
    String userAnswer(String question)

    //only display
    def displayDecisionOverview(String decisions)
}
