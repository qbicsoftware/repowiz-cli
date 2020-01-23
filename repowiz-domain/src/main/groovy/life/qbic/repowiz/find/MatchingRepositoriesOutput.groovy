package life.qbic.repowiz.find

import life.qbic.repowiz.Repository

interface MatchingRepositoriesOutput {

    def transferRepositoryList(List<Repository>repositories)

}