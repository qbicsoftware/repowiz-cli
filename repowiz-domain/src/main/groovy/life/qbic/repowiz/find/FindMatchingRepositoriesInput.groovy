package life.qbic.repowiz.find

import life.qbic.repowiz.find.submissionTypes.Organism

interface FindMatchingRepositoriesInput {

    def startGuide()
    def nextAnswerPossibility(String answer)
    //def suggestRepos(HashMap<String,String> submissionSpecification)

}