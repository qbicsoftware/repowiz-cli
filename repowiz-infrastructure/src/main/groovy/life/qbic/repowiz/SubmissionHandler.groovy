package life.qbic.repowiz

import life.qbic.repowiz.cli.SubmissionPresenter
import life.qbic.repowiz.find.MatchingRepositoriesOutput
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.select.SelectRepositoryOutput

//todo do i really want to keep one class implementing so many interfaces?
class SubmissionHandler implements MatchingRepositoriesOutput, SelectRepositoryOutput{

    SelectRepositoryInput repositoryInput
    SubmissionPresenter presenter

    SubmissionHandler(SubmissionPresenter presenter){
        this.presenter = presenter
    }

    SubmissionHandler(SelectRepositoryInput repositoryInput, SubmissionPresenter presenter){
        this.repositoryInput = repositoryInput
        this.presenter = presenter
    }

    def addRepositoryInput(SelectRepositoryInput input){
        this.repositoryInput = input
    }

    //MatchingRepositories output
    @Override
    String transferAnswerPossibilities(List<String> choices) {
        presenter.requestAnswer(choices)
    }

    @Override
    String transferDecisionStack(List<String> decisions) {
        presenter.displayDecisions(decisions)
    }

    @Override
    def transferRepositoryList(List<Repository> repositories) {
        //let the user decide which repo he wants
        List<String> repoNames = []

        repositories.each {
            repoNames << it.name
        }

        String choice = presenter.chooseRepository(repoNames)

        //or transfer to repository input
        repositories.each {
            if(it.name == choice)
                repositoryInput.suggestedRepository(it)
        }
    }

    //selectRepository output
    @Override
    def selectedRepository(Repository repository) {
        println "You selected $repository.name"
    }
}
