package life.qbic.repowiz.cli

import life.qbic.repowiz.observer.AnswerTypes

class SubmissionPresenter {

    CommandlineView output

    SubmissionPresenter(CommandlineView output){
        this.output = output
    }

    String requestAnswer(AnswerTypes type, List < String > choices){
        HashMap map = listToMap(choices) //todo rename: adds numbers for the users choice
        output.displayQuestion(type,map)
    }

    def displayUserChoices(List<String> decisions){
        output.displayDecisionOverview(decisions)
    }

    def displayUserInformation(List<String> decisions){
        output.displayDecisionOverview(decisions)
    }

    def listToMap(List elements){
        HashMap map = new HashMap()
        int counter = 1

        elements.each {
            map.put(counter, it)
            counter ++
        }
        return map
    }


}
