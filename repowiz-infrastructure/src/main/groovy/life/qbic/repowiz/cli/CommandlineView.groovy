package life.qbic.repowiz.cli

import life.qbic.repowiz.observer.AnswerTypes
import life.qbic.repowiz.observer.UserAnswer

interface CommandlineView {

    //handle input
    void displayQuestion(AnswerTypes type, HashMap <Integer,String> choices)

    //only display
    void displayDecisionOverview(List<String> decisions)
    void displayInformation(List<String> info)
    void displayInformation(String info)
    void displayList(List<String> list)
    void displaySummaryProject(HashMap<String,String> projectInfo, String id)
    void displaySummarySamples(HashMap<String,HashMap<String,String>> samples)

    void setUserAnswer(UserAnswer answer)

}
