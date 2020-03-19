package life.qbic.repowiz.cli

import life.qbic.repowiz.observer.AnswerTypes

class SubmissionPresenter {

    CommandlineView output
    SubmissionController controller

    SubmissionPresenter(CommandlineView output, SubmissionController controller){
        this.output = output
        this.controller = controller
    }

    String requestAnswer(AnswerTypes type, List < String > choices){

        HashMap map = listToMap(choices) //todo rename: adds numbers for the users choice
        output.displayQuestion(type,map)
    }

    def displayUserChoices(List<String> decisions){

        output.displayDecisionOverview(decisions)
    }


    /*def requestDecision(List<String> choices){
        String answer = requestAnswer(choices)
        controller.transferDecision(answer) //transferToFindRepository
    }

    def requestRepository(List<String> repos){
        String answer = requestAnswer(repos)
        controller.transferRepositoryName(answer)
    }

    def requestUploadType(List<String> uploadTypes){
        String answer = requestAnswer(uploadTypes)
        controller.transferUploadType(answer) //transferToSelectRepository
    }*/


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
