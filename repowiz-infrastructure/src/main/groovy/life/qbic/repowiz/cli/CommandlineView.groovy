package life.qbic.repowiz.cli

import life.qbic.repowiz.observer.AnswerTypes
import life.qbic.repowiz.observer.UserAnswer

interface CommandlineView {

    //handle input
    void displayQuestion(AnswerTypes type, HashMap <Integer,String> choices)

    //only display
    void displayDecisionOverview(List<String> decisions)

    void setUserAnswer(UserAnswer answer)

}
