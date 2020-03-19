package life.qbic.repowiz

import life.qbic.repowiz.cli.SubmissionPresenter
import life.qbic.repowiz.finalise.SubmissionOutput
import life.qbic.repowiz.find.MatchingRepositoriesOutput
import life.qbic.repowiz.observer.AnswerTypes
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.select.SelectRepositoryOutput
import life.qbic.repowiz.finalise.FinaliseSubmission

//todo do i really want to keep one class implementing so many interfaces?
class SubmissionHandler implements MatchingRepositoriesOutput, SelectRepositoryOutput, PrepareSubmissionOutput, SubmissionOutput{

    SubmissionPresenter presenter

    SelectRepositoryInput repositoryInput
    PrepareSubmissionInput prepareSubmissionInput
    FinaliseSubmission finaliseSubmissionInput

    SubmissionHandler(SubmissionPresenter presenter){
        this.presenter = presenter
    }

    SubmissionHandler(SelectRepositoryInput repositoryInput, SubmissionPresenter presenter){
        this.repositoryInput = repositoryInput
        this.presenter = presenter
    }

    SubmissionHandler(PrepareSubmissionInput prepareSubmissionInput, SubmissionPresenter presenter){
        this.prepareSubmissionInput = prepareSubmissionInput
        this.presenter = presenter
    }

    SubmissionHandler(FinaliseSubmission finaliseSubmissionInput, SubmissionPresenter presenter){
        this.finaliseSubmissionInput = finaliseSubmissionInput
        this.presenter = presenter
    }

    //MatchingRepositories output
    @Override
    def transferAnswerPossibilities(List<String> choices) {
        presenter.requestAnswer(AnswerTypes.DECISION,choices)
    }

    @Override
    def transferDecisionStack(List<String> decisions) {
        presenter.displayUserChoices(decisions)
    }

    @Override
    def transferRepositoryList(List<Repository> repositories) {
        //let the user decide which repo he wants
        repositoryInput.selectRepoFromSuggestions(repositories)
    }

    //selectRepository output
    @Override
    def chooseRepository(List<String> repositories) {
        presenter.requestAnswer(AnswerTypes.REPOSITORY, repositories)
    }

    @Override
    def selectedRepository(Repository repository) {
        presenter.displayUserChoices([repository.name])
        prepareSubmissionInput.prepareSubmissionToRepository(repository)
    }

    //prepare submission use case --> transfer to finalise submission
    @Override
    def validateProject(RepoWizProject project, List<RepoWizSample> samples) {
        finaliseSubmissionInput.transferSubmissionData(project,samples)
    }

    @Override
    def submissionDetails(String repoName, String uploadType) {
        finaliseSubmissionInput.setSubmissionDetails(repoName,uploadType)
    }

    @Override
    def transferQuestion(List<String> uploadTypes) { //todo rename: transfer uploadtypes from usecase to handler
        presenter.requestAnswer(AnswerTypes.UPLOADTYPE,uploadTypes)
    }

    @Override
    def displayAnswer(String choice) { //todo rename: selected uploadtype is shown
        presenter.displayUserChoices([choice])
    }

    //finalise submission output
    @Override
    def submissionSummary(String summary) {
        return null
    }

    @Override
    String verifySubmission() {
        finaliseSubmissionInput
    }

    @Override
    List<String> subsequentSteps() {
        return null
    }

}
