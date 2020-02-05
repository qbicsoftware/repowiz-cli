package life.qbic.repowiz.cli

interface CommandlineView {

    //handle input
    String userAnswer(String question)

    //only display
    def displayDecisionOverview(String decisions)

}
