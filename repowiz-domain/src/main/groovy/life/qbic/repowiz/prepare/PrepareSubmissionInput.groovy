package life.qbic.repowiz.prepare

import life.qbic.repowiz.Repository

interface PrepareSubmissionInput {

    def prepareSubmissionToRepository(Repository repository)
    def processUploadType(String answer)
}