package life.qbic.repowiz.cli

import life.qbic.repowiz.observer.AnswerTypes
import life.qbic.repowiz.observer.UserAnswer

/**
 * Interface defining view methods
 */
interface CommandlineView {

    /**
     * Displays answer possibilities with the type of expected answer to define the question type
     * @param type
     * @param choices
     */
    void displayQuestion(AnswerTypes type, HashMap <Integer,String> choices)

    /**
     * Displays decision of the user to give feedback on made decisions
     * @param decisions
     */
    void displayDecisionOverview(List<String> decisions)

    /**
     * Displays text that contains multiple information
     * @param info
     */
    void displayInformation(List<String> info)

    /**
     * Displays information to the user
     * @param info
     */
    void displayInformation(String info)

    /**
     * Displays content of a list as a list to the user
     * @param list
     */
    void displayList(List<String> list)

    /**
     * Displays a summary of the project with its properties to the user
     * @param projectInfo
     * @param id
     */
    void displaySummaryProject(HashMap<String,String> projectInfo, String id)

    /**
     * Displays a summary of all samples with their properties
     * @param samples
     */
    void displaySummarySamples(HashMap<String,HashMap<String,String>> samples)

    /**
     * Sets the user answer in the observer UserAnswer
     * @param answer
     */
    void setUserAnswer(UserAnswer answer)

}
