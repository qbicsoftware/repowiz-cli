package life.qbic.repowiz.application.view;

import life.qbic.repowiz.cli.CommandlineView;
import life.qbic.repowiz.observer.AnswerTypes;
import life.qbic.repowiz.observer.UserAnswer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RepoWizView implements CommandlineView {

    private UserAnswer answer;

    @Override
    public void displayQuestion(AnswerTypes type, HashMap<Integer,String> question) {
        String formattedChoices = "> Please choose one of the following options: \n";
        formattedChoices += mapToString(question); //todo rename: creates string from map for view

        System.out.println(formattedChoices.trim());
        Scanner scan = new Scanner(System.in);
        int answerNum = scan.nextInt();

        String userAnswer = question.get(answerNum).toLowerCase();

        answer.setAnswer(type,userAnswer);
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
            formattedDecisions.append(" " + entry +"\n");
        }

        System.out.println(formattedDecisions);
    }

    public void displayList(List<String> list){
        StringBuilder information = new StringBuilder("[ ");

        for(String info:list){
            information.append(info+", ");
        }
        //delete last comma
        information.deleteCharAt(information.length()-1);
        information.append(" ]");

        System.out.println(information);
    }

    @Override
    public void displayInformation(String info) {
        System.out.println("> " + info);
    }

    @Override
    public void setUserAnswer(UserAnswer answer) {
        this.answer = answer;
    }

    private String mapToString(HashMap<Integer, String> map){
        StringBuilder text = new StringBuilder();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
                text.append("> "+"(" + entry.getKey() + ") " + entry.getValue() + "\n");
        }
        return text.toString();
    }

}
