package life.qbic.repowiz

interface SubmissionSpecificationInput {

    //project code or specification of project data?
    def suggestReposForProject(String projectID)
    def getRepositoryInfo(String repository)

}