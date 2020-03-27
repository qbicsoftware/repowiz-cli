package life.qbic.repowiz

import life.qbic.repowiz.model.RepoWizProject
import life.qbic.repowiz.model.RepoWizSample


interface RepositoryInput {
    def createWorkbook(String template)
    def determineMissingValues(RepoWizProject project, List<RepoWizSample> sampples)
}