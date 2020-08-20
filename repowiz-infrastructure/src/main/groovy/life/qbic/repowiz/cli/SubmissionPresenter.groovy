package life.qbic.repowiz.cli

import life.qbic.repowiz.observer.AnswerTypes

/**
 * This class presents data to the view
 *
 * This class should be used to delegate data from RepoWiz to the view
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class SubmissionPresenter {

    private final CommandlineView output

    /**
     * Creates a presenter instance based on a given CommandlineView
     * @param output defines how data can be delegated out of this class
     */
    SubmissionPresenter(CommandlineView output) {
        this.output = output
    }

    /**
     * Should be called to delegate a question to the user to the output
     * @param type of answers that can be given
     * @param choices as a list of answer choices the user has
     */
    void requestAnswer(AnswerTypes type, List<String> choices) {
        Map map = formatList(choices) //todo rename: adds numbers for the users choice
        output.displayQuestion(type, map)
    }

    /**
     * Should be called to display a list of decisions
     * @param decisions that the user made before
     */
    void displayUserDecisions(List<String> decisions) {
        output.displayDecisionOverview(decisions)
    }

    /**
     * Displays the information to the user
     * @param infos as a list of strings
     */
    void displayUserInformation(List<String> infos) {
        output.displayList(infos)
    }

    /**
     * Displays a string of information to the user
     * @param info that should be displayed to the user
     */
    void displayUserInformation(String info) {
        output.displayInformation(info)
    }

    /**
     * Displays project information to the user based on project properties and the project id
     * @param project described by its properties
     * @param id which specifies the project by its id
     */
    void displayProjectInfo(Map project, String id) {
        output.displaySummaryProject(project, id)
    }

    /**
     * Displays the information of a sample to the user
     * @param samples with information of their properties
     */
    void displaySampleInfo(Map<String, Map> samples) {
        output.displaySummarySamples(samples)
    }

    /**
     * Formats the list of elements into a Map
     * @param elements with information in a lista
     * @return a map containing the elements of the list
     */
    Map formatList(List elements) {
        HashMap map = new HashMap()
        int counter = 1

        elements.each {
            map.put(counter, it)
            counter++
        }
        return map
    }

}
