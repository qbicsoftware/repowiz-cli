package life.qbic.repowiz.cli

import life.qbic.repowiz.model.SubmissionModel
import life.qbic.repowiz.observer.AnswerTypes

class SubmissionPresenter {

    CommandlineView output

    SubmissionPresenter(CommandlineView output){
        this.output = output
    }

    void requestAnswer(AnswerTypes type, List < String > choices){
        HashMap map = formatList(choices) //todo rename: adds numbers for the users choice
        output.displayQuestion(type,map)
    }

    def displayUserDecisions(List<String> decisions){
        output.displayDecisionOverview(decisions)
    }

    def displayUserInformation(List<String> infos){
        output.displayList(infos)
    }

    def displayUserInformation(String info){
        output.displayInformation(info)
    }

    def displayProjectInfo(HashMap project, String id){
        output.displaySummaryProject(project,id)
    }

    def displaySampleInfo(HashMap<String, HashMap> samples){
        output.displaySummarySamples(samples)
    }

    def formatList(List elements){
        HashMap map = new HashMap()
        int counter = 1

        elements.each {
            map.put(counter, it)
            counter ++
        }
        return map
    }

}
