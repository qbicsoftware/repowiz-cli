package life.qbic.repowiz.application.view;

import life.qbic.repowiz.CommandlineView;

import java.util.Scanner;

public class RepoWizView implements CommandlineView {
    @Override
    public String displayQuestion(String question) {
        System.out.println(question);
        Scanner scan = new Scanner(System.in);

        String answer = scan.nextLine();

        return answer;
    }

    @Override
    public Object displayDecisions(String decisions) {
        System.out.println(decisions);
        return null;
    }
}
