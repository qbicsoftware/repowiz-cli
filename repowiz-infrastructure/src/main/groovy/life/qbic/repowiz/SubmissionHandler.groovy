package life.qbic.repowiz

import life.qbic.repowiz.cli.SubmissionPresenter
import life.qbic.repowiz.find.MatchingRepositoriesOutput
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.select.SelectRepositoryOutput

//todo do i really want to keep one class implementing so many interfaces?
class SubmissionHandler implements MatchingRepositoriesOutput, SelectRepositoryOutput, PrepareSubmissionOutput{

    SelectRepositoryInput repositoryInput
    SubmissionPresenter presenter
    PrepareSubmissionInput prepareSubmission

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

    def addPrepareSubmissionInput(PrepareSubmissionInput input){
        prepareSubmission = input
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
        repositoryInput.selectRepoFromSuggestions()
    }

    //selectRepository output
    @Override
    String chooseRepository(List<String> repositories) {
        presenter.chooseRepository(repositories)
    }

    @Override
    def selectedRepository(Repository repository) {
        //println "You selected $repository.name"
        prepareSubmission.prepareSubmissionToRepository(repository)
    }

    @Override
    List<String> transferProjectFiles() {
        return null
    }

    @Override
    def transferProjectMetadata() {
        return null
    }
}
