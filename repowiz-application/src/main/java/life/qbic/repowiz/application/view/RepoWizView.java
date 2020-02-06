package life.qbic.repowiz.application.view;

import life.qbic.repowiz.cli.CommandlineView;

import java.util.Scanner;

public class RepoWizView implements CommandlineView {

    @Override
    public String userAnswer(String question) {
        System.out.println(question);
        Scanner scan = new Scanner(System.in);

        return scan.nextLine();
    }

    @Override
    public Object displayDecisionOverview(String decisions) {
        System.out.println(decisions);
        return null;
    }
}
