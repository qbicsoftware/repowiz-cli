package life.qbic.repowiz

import life.qbic.repowiz.find.MatchingRepositoriesOutput
import life.qbic.repowiz.select.SelectRepositoryInput

class SubmissionHandler implements MatchingRepositoriesOutput{

    SelectRepositoryInput repositoryInput

    SubmissionHandler(SelectRepositoryInput repositoryInput){
        this.repositoryInput = repositoryInput
    }

    @Override
    def transferAnswerPossibilities(List<String> choices) {
        return null
    }

    @Override
    def transferDecisionStack(List<String> decisions) {
        return null
    }

    @Override
    def transferRepositoryList(List<Repository> repositories) { //
        //user choose valid repository
        repositoryInput.suggestedRepository(repositories)
    }

}
