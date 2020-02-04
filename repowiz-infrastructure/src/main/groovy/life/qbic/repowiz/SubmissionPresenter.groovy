package life.qbic.repowiz

class SubmissionPresenter {

    CommandlineView output

    SubmissionPresenter(CommandlineView output){
        this.output = output
    }

    String requestAnswer(List<String> choices){

        String formattedChoices = "Please choose one of the following options: \n"

        choices.each {
            formattedChoices += "$it "
        }
        
        String answer = output.userAnswer(formattedChoices)

        return answer
    }

    def displayDecisions(List<String> decisions){

        String formattedDecisions = "You selected: "

        decisions.each {
            formattedDecisions += "$it "
        }

        output.displayDecisionOverview(formattedDecisions)
    }


}
