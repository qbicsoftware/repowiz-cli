package life.qbic.repowiz

import life.qbic.repowiz.find.MatchingRepositoriesOutput
import life.qbic.repowiz.select.SelectRepositoryInput

class SubmissionHandler implements MatchingRepositoriesOutput{

    SelectRepositoryInput repositoryInput

    SubmissionHandler(SelectRepositoryInput repositoryInput){
        this.repositoryInput = repositoryInput
    }

    @Override
    def transferRepositoryList(List<Repository> repositories) { //
        //user choose valid repository
        repositoryInput.suggestedRepository(repositories)
    }

}
