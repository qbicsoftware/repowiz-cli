package life.qbic.repowiz.application.view;

import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.observer.AnswerTypes;
import life.qbic.repowiz.observer.UserAnswer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class handles how elements are viewable via the commandline
 *
 * This class is should be used to define what components are part of the view and how to they are displayed
 *
 * @since: 1.0.0
 * @author: Jennifer Bödker
 *
 */
public class RepoWizView implements CommandlineView {

    private UserAnswer answer;

    @Override
    public void displayQuestion(AnswerTypes type, HashMap<Integer, String> question) {
        String formattedChoices = "> Please choose one of the following options: \n";
        formattedChoices += mapToString(question); //todo rename: creates string from map for view

        System.out.println(formattedChoices.trim());
        Scanner scan = new Scanner(System.in);
        int answerNum = scan.nextInt();

        String userAnswer = question.get(answerNum).toLowerCase();

        answer.setAnswer(type, userAnswer);
    }

    @Override
    public void displayDecisionOverview(List<String> decisions) {
        StringBuilder formattedDecisions = new StringBuilder("> You selected: ");

        for (String entry : decisions) {
            formattedDecisions.append("-> " + entry);
        }

        System.out.println(formattedDecisions);
    }

    @Override
    public void displayInformation(List<String> info) {
        StringBuilder formattedDecisions = new StringBuilder("> ");

        for (String entry : info) {
            formattedDecisions.append(" " + entry + "\n");
        }

        System.out.println(formattedDecisions);
    }

    /**
     * Displays a list of strings in the command line
     * @param list each string in the list contains information that is meant to be displayed to the user
     */
    public void displayList(List<String> list) {
        StringBuilder information = new StringBuilder();

        for (String info : list) {
            information.append(" - " + info + "\n");
        }

        System.out.println(information);
    }

    @Override
    public void displaySummaryProject(HashMap<String, String> projectInfo, String id) {
        StringBuilder summary = new StringBuilder();
        //project
        summary.append("> Project " + id + " is described as follows: \n");

        for (Map.Entry<String, String> info : projectInfo.entrySet()) {
            summary.append(" " + info.getKey() + ": " + info.getValue() + "\n");
        }

        System.out.println(summary);
    }

    @Override
    public void displaySummarySamples(HashMap<String, HashMap<String, String>> samples) {
        StringBuilder summary = new StringBuilder();
        //samples
        for (Map.Entry<String, HashMap<String, String>> sample : samples.entrySet()) {
            summary.append("> " + sample.getKey() + " is described as follows: \n");

            for (Map.Entry<String, String> info : sample.getValue().entrySet()) {
                summary.append(" " + info.getKey() + ": " + info.getValue() + "\n");
            }
        }

        System.out.println(summary);
    }

    @Override
    public void displayInformation(String info) {
        System.out.println("> " + info);
    }

    @Override
    public void setUserAnswer(UserAnswer answer) {
        this.answer = answer;
    }

    /**
     * Creates a String from a map whereas each key value is printed next its value in a single line
     * @param map with Integers as keys and Strings as values
     * @return text with information of the map
     */
    private String mapToString(HashMap<Integer, String> map) {
        StringBuilder text = new StringBuilder();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            text.append("> " + "(" + entry.getKey() + ") " + entry.getValue() + "\n");
        }
        return text.toString();
    }

}
