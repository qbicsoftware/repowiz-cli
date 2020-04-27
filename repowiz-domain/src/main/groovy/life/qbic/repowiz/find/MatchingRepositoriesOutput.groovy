package life.qbic.repowiz.find

import life.qbic.repowiz.Repository

interface MatchingRepositoriesOutput {

    def transferAnswerPossibilities(List<String> choices)
    def transferDecisionStack(List<String> decisions)
    def transferRepositoryStandard(String standard)
    def transferRepositoryList(List<Repository>repositories)

}