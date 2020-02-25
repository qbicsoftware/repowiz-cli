package life.qbic.repowiz.cli

import life.qbic.repowiz.prepare.UserInputController
import life.qbic.repowiz.submit.SubmissionOutput

class SubmissionPresenter implements SubmissionOutput{

    CommandlineView output
    UserInputController controllerUI

    SubmissionPresenter(CommandlineView output){
        this.output = output
    }


    String requestAnswer(HashMap<Integer,String> choices){

        String formattedChoices = "> Please choose one of the following options: \n> "

        choices.each {num,choice ->
            formattedChoices += "$choice ($num) "
        }
        
        int answerNumber = output.userAnswer(formattedChoices)
        //controllerUI.transferUserAnswer(answer)

        return choices.get(answerNumber)
    }

    def displayDecisions(List<String> decisions){

        String formattedDecisions = "> You selected: "

        decisions.each {
            formattedDecisions += "$it -> "
        }

        output.displayDecisionOverview(formattedDecisions)
    }

    def chooseRepository(List<String> repos){
        String choose = "> Please choose one of the following repositories: "

        repos.each {
            choose += "\n> $it"
        }

        String answer = output.userAnswer(choose)
        //controllerUI.transferUserAnswer(answer)

        return answer.toLowerCase()
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
