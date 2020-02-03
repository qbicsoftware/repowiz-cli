package life.qbic.repowiz

class SubmissionPresenter {

    CommandlineOutput output

    SubmissionPresenter(CommandlineOutput output){
        this.output = output
    }

    def displayChoice(List<String> choices){

        String formattedChoices = "Please choose one of the following options: \n"

        choices.each {
            formattedChoices << "$it "
        }
        
        output.displayText(formattedChoices)
    }

    def displayDecisions(List<String> decisions){

        String formattedDecisions = "You selected: "

        decisions.each {
            formattedDecisions << "$it "
        }

        output.displayText(formattedDecisions)
    }


}
