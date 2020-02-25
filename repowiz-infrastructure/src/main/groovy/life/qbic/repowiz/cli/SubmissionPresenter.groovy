package life.qbic.repowiz.cli


import life.qbic.repowiz.submit.SubmissionOutput

class SubmissionPresenter implements SubmissionOutput{

    CommandlineView output
    SubmissionController controller

    SubmissionPresenter(CommandlineView output, SubmissionController controller){
        this.output = output
        this.controller = controller
    }

    String requestAnswer(List<String> choices){
        String formattedChoices = "> Please choose one of the following options: \n> "

        HashMap map = listToMap(choices) //todo rename: adds numbers for the users choice

        formattedChoices += mapToString(map) //todo rename: creates string from map for view

        int answerNumber = output.userAnswer(formattedChoices)

        String answer = map.get(answerNumber).toLowerCase()
        return answer
    }

    def requestDecision(List<String> choices){
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
    }

    def displayUserChoices(List<String> decisions){ //choice

        String formattedDecisions = "> You selected: "

        decisions.each {
            formattedDecisions += "-> $it "
        }

        output.displayDecisionOverview(formattedDecisions)
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

    def mapToString(HashMap<Integer,String> map){
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
