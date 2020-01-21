package life.qbic.repowiz.find

import life.qbic.repowiz.Repository

interface FindMatchingRepositoriesInput {

    def suggestRepos(HashMap<String,String> submissionSpecification)

}