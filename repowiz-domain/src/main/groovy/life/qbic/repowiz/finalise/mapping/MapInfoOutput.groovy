package life.qbic.repowiz.prepare.mapping

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample

interface MapInfoOutput {

    def transferUpdatedProjectData(RepoWizProject project, List<RepoWizSample> samples) //todo do we need this since we work on the same objects that are updated

}