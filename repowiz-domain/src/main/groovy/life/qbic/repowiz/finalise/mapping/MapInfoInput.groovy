package life.qbic.repowiz.prepare.mapping

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface MapInfoInput {

    //def getFields(String uploadType)
    def addOutput(MapInfoOutput output) //todo do that somewhere else
    def determineRepositoryPlugin(String repositoryName, String uploadType)
    def transferDataToRepositoryPlugin(RepoWizProject project, List<RepoWizSample> samples)


}