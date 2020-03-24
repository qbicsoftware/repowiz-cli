package life.qbic.repowiz

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface RepositoryInput {
    def parseAsStream(String template)
    def determineMissingValues(RepoWizProject project, List<RepoWizSample> sampples)
}