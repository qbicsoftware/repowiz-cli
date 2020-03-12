package life.qbic.repowiz.finalise.parsing

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface RepositoryInput {

    def parseAsStream(String template) //return sheets?
    def validateProjectInformation(RepoWizProject project, List<RepoWizSample> samples)
    String getRepositoryName() //todo create abstract class and set this as class variable?
    def addRepositoryOutput(RepositoryOutput out)
    def createSubmission(RepoWizProject project, List<RepoWizSample> samples)

}