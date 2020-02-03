package life.qbic.repowiz

import life.qbic.repowiz.find.MatchingRepositoriesOutput
import life.qbic.repowiz.select.SelectRepositoryInput

class SubmissionHandler implements MatchingRepositoriesOutput{

    SelectRepositoryInput repositoryInput
    SubmissionPresenter presenter

    SubmissionHandler(SubmissionPresenter presenter){
        this.presenter = presenter
    }

    SubmissionHandler(SelectRepositoryInput repositoryInput, SubmissionPresenter presenter){
        this.repositoryInput = repositoryInput
        this.presenter = presenter
    }

    @Override
    String transferAnswerPossibilities(List<String> choices) {
        presenter.displayChoice(choices)
    }

    @Override
    String transferDecisionStack(List<String> decisions) {
        presenter.displayDecisions(decisions)
    }

    @Override
    def transferRepositoryList(List<Repository> repositories) {
        repositoryInput.suggestedRepository(repositories)
    }

}
