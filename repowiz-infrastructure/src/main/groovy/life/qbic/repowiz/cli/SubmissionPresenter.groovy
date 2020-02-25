package life.qbic.repowiz.cli

import life.qbic.repowiz.prepare.UserInputController
import life.qbic.repowiz.submit.SubmissionOutput

class SubmissionPresenter implements SubmissionOutput{

    CommandlineView output
    UserInputController controllerUI

    SubmissionPresenter(CommandlineView output){
        this.output = output
    }


    String requestAnswer(List<String> choices){

        String formattedChoices = "> Please choose one of the following options: \n> "

        HashMap map = mapWithNumber(choices)
        formattedChoices += numberMapToString(map)

        int answerNumber = output.userAnswer(formattedChoices)
        String answer = map.get(answerNumber)
        //controllerUI.transferUserAnswer(answer)

        return answer.toLowerCase()
    }

    def displayDecisions(List<String> decisions){

        String formattedDecisions = "> You selected: "

        decisions.each {
            formattedDecisions += "-> $it "
        }

        output.displayDecisionOverview(formattedDecisions)
    }

    def chooseRepository(List<String> repos){
        String choose = "> Please choose one of the following repositories: \n>"

        HashMap<Integer,String> map = mapWithNumber(repos)

        choose += numberMapToString(map)

        int answerNum = output.userAnswer(choose)
        String answer = map.get(answerNum)

        //controllerUI.transferUserAnswer(answer)

        return answer.toLowerCase()
    }

    def mapWithNumber(List elements){
        HashMap map = new HashMap()
        int counter = 1

        elements.each {
            map.put(counter, it)
            counter ++
        }
        return map
    }

    def numberMapToString(HashMap<Integer,String> map){
        String text = ""
        map.each {num, val ->
            text += " $val ($num)"
        }

        return text
    }


    //keep it like that? does it make sense to show this content to the presenter?

    @Override
    def getSampleIds(List<String> sampleIDs) {
        return null
    }

    @Override
    def getMetaDataForSamples(HashMap<String, String> metadata, String sampleID) {
        return null
    }

    @Override
    def getFilesForSamples(List<String> filePaths, String sampleID) {
        return null
    }

    @Override
    def uploadFormat(String type) {
        return null
    }

    @Override
    List<String> subsequentSteps() {
        return null
    }

    @Override
    String submissionIdentifier() {
        return null
    }
}
