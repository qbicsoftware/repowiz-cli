package life.qbic.repowiz.cli

interface CommandlineView {

    //handle input
    int userAnswer(String question)

    //only display
    def displayDecisionOverview(String decisions)

}
