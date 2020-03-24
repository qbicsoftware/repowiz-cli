package life.qbic.repowiz.finalise

import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class FinaliseSubmissionImpl implements VerifySubmission, FinaliseSubmission{

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
    def setSubmissionDetails(String repositoryName, String uploadType) {
        targetRepository.determineRepositoryPlugin(repositoryName, uploadType)
    }

    //todo connect with controller to receive answer
    @Override
    def verifyCorrectnessOfSubmission(boolean correct) {
        if(correct){
            //todo fill template
            //todo start download
        }
    }

    //MapInfoOutput
    @Override
    def transferUpdatedProjectData(RepoWizProject project, List<RepoWizSample> samples) {

    }

    //SubmissionOutput
    @Override
    def transferSubmissionData(RepoWizProject project, List<RepoWizSample> samples) {
        return null
    }

}