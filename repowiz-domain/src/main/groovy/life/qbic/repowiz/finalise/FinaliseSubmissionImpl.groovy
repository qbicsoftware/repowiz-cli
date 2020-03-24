package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import life.qbic.repowiz.prepare.model.SubmissionModel
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class FinaliseSubmissionImpl implements FinaliseSubmission{

    SubmissionOutput output
    TargetRepository targetRepository

    private static final Logger LOG = LogManager.getLogger(FinaliseSubmissionImpl.class)


    FinaliseSubmissionImpl(TargetRepository targetRepository){
        this.targetRepository = targetRepository

        //pluginHandler.addOutput(this)
    }

    def addSubmissionHandler(SubmissionOutput out){
        output = out
    }

    @Override
    def transferSubmissionData(SubmissionModel submission, Repository repository) {
        return null
    }

    @Override
    def processVerificationOfSubmission(boolean verified) {
        if(verified){
            //todo fill template
            //todo start download
        }
        return null
    }


}