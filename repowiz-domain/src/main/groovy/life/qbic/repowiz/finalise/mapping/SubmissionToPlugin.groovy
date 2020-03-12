package life.qbic.repowiz.finalise.mapping

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface SubmissionToPlugin {

    //def getFields(String uploadType)
    def addOutput(PluginToSubmission output) //todo do that somewhere else
    def determineRepositoryPlugin(String repositoryName, String uploadType)
    def transferDataToRepositoryPlugin(RepoWizProject project, List<RepoWizSample> samples)


}