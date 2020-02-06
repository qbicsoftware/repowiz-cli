package life.qbic.repowiz.cli

import life.qbic.repowiz.cli.CommandlineView

class SubmissionPresenter {

    CommandlineView output

    SubmissionPresenter(CommandlineView output){
        this.output = output
    }

    String requestAnswer(List<String> choices){

        String formattedChoices = "> Please choose one of the following options: \n> "

        choices.each {
            formattedChoices += "$it, "
        }
        
        String answer = output.userAnswer(formattedChoices)

        return answer.toLowerCase()
    }

    def displayDecisions(List<String> decisions){

        String formattedDecisions = "> You selected: "

        decisions.each {
            formattedDecisions += "$it "
        }

        output.displayDecisionOverview(formattedDecisions)
    }

    def chooseRepository(List<String> repos){
        String choose = "> Please choose one of the following repositories: "

        repos.each {
            choose += "\n> $it"
        }

        String answer = output.userAnswer(choose)

        return answer.toLowerCase()
    }


}
