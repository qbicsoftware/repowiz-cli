package life.qbic.repowiz

import life.qbic.repowiz.cli.SubmissionPresenter
import life.qbic.repowiz.finalise.FinaliseSubmission
import life.qbic.repowiz.finalise.SubmissionOutput
import life.qbic.repowiz.find.MatchingRepositoriesOutput
import life.qbic.repowiz.model.SubmissionModel
import life.qbic.repowiz.observer.AnswerTypes
import life.qbic.repowiz.prepare.PrepareSubmissionInput
import life.qbic.repowiz.prepare.PrepareSubmissionOutput
import life.qbic.repowiz.select.SelectRepositoryInput
import life.qbic.repowiz.select.SelectRepositoryOutput

/**
 * This class serves as an interactor or connector of all use cases.
 *
 * This class should be used whenever data needs to be orchestrated between different use cases. It implements the output interfaces
 * of these interfaces and delegates the data obtained by the use cases to the next use case
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class SubmissionHandler implements MatchingRepositoriesOutput, SelectRepositoryOutput, PrepareSubmissionOutput, SubmissionOutput {

    private SubmissionPresenter presenter

    private SelectRepositoryInput repositoryInput
    private PrepareSubmissionInput prepareSubmissionInput
    private FinaliseSubmission finaliseSubmissionInput

    /**
     * Creates a submission handler with a presenter
     *
     * @param presenter which defines how data is presented to the user
     */
    SubmissionHandler(SubmissionPresenter presenter) {
        this.presenter = presenter
    }

    /**
     * Creates a submission handler for delegating data from the select repository use case into other classes
     *
     * @param repositoryInput which allows the class to forward data to the select repository use case
     * @param presenter which defines how data is presented to the user
     */
    SubmissionHandler(SelectRepositoryInput repositoryInput, SubmissionPresenter presenter) {
        this.repositoryInput = repositoryInput
        this.presenter = presenter
    }

    /**
     * Creates a submission handler for delegating data from the prepare submission use case into other classes
     *
     * @param prepareSubmissionInput which allows the class to forward data to the prepare submission use case
     * @param presenter which defines how data is presented to the user
     */
    SubmissionHandler(PrepareSubmissionInput prepareSubmissionInput, SubmissionPresenter presenter) {
        this.prepareSubmissionInput = prepareSubmissionInput
        this.presenter = presenter
    }

    /**
     * Creates a submission handler for delegating data from the finalise submission use case into other classes
     *
     * @param finaliseSubmissionInput which allows the class to forward data to the finalise submission use case
     * @param presenter which defines how data is presented to the user
     */
    SubmissionHandler(FinaliseSubmission finaliseSubmissionInput, SubmissionPresenter presenter) {
        this.finaliseSubmissionInput = finaliseSubmissionInput
        this.presenter = presenter
    }

    @Override
    void transferAnswerPossibilities(List<String> choices) {
        presenter.requestAnswer(AnswerTypes.DECISION, choices)
    }

    @Override
    void transferDecisionStack(List<String> decisions) {
        presenter.displayUserDecisions(decisions)
    }

    @Override
    void transferUserInformation(String standard) {
        presenter.displayUserInformation(standard)
    }

    @Override
    void transferRepositoryList(List<Repository> repositories) {
        // We want the user to decide which repo to submit to.
        repositoryInput.selectRepoFromSuggestions(repositories)
    }

    //selectRepository output
    @Override
    def chooseRepository(List<String> repositories) {
        presenter.requestAnswer(AnswerTypes.REPOSITORY, repositories)
    }

    @Override
    def selectedRepository(Repository repository) {
        presenter.displayUserDecisions([repository.repositoryName])
        prepareSubmissionInput.prepareSubmissionToRepository(repository)
    }

    //prepare submission use case --> transfer to finalise submission
    @Override
    def finaliseSubmission(SubmissionModel submission, Repository repository) {
        finaliseSubmissionInput.transferSubmissionData(submission, repository)
    }

    @Override
    def transferQuestion(List<String> uploadTypes) { //todo rename: transfer uploadtypes from usecase to handler
        presenter.requestAnswer(AnswerTypes.UPLOADTYPE, uploadTypes)
    }

    @Override
    def displayAnswer(String choice) { //todo rename: selected uploadtype is shown
        presenter.displayUserDecisions([choice])
    }

    //finalise submission output
    @Override
    void displayUserInformation(String information) {
        presenter.displayUserInformation(information)
    }

    @Override
    void displayUserInformation(List<String> text) {
        presenter.displayUserInformation(text)
    }

    @Override
    void displayProjectSummary(Map project, String id) {
        presenter.displayProjectInfo(project, id)
    }

    @Override
    void displaySampleSummary(Map<String, Map> samples) {
        presenter.displaySampleInfo(samples)
    }

    @Override
    void verifySubmission(List verify) {
        presenter.requestAnswer(AnswerTypes.SUBMIT, verify)
    }

}
