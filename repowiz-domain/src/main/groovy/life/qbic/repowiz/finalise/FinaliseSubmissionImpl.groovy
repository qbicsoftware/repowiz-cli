package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider
import life.qbic.repowiz.prepare.model.SubmissionModel
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class FinaliseSubmissionImpl implements FinaliseSubmission{

    SubmissionOutput output
    TargetRepository targetRepository
    Repository repository
    SubmissionManager manager

    private static final Logger LOG = LogManager.getLogger(FinaliseSubmissionImpl.class)


    FinaliseSubmissionImpl(TargetRepository targetRepository, SubmissionOutput out){
        this.targetRepository = targetRepository
        output = out
    }

    @Override
    def transferSubmissionData(SubmissionModel submission, Repository repository) {
        LOG.info "Transfer data to TargetRepositoryProvider"
        this.repository = repository

        TargetRepositoryProvider provider = targetRepository.provider(repository.name)
        provider.setUploadType(submission.uploadType)

        manager = provider.create()
        //output.displayInformation("Validate submission data")
        submission = manager.validateSubmissionModel(submission)

        output.displaySubmissionSummary(submissionModelToString(submission))
        output.verifySubmission()
    }

    static String submissionModelToString(SubmissionModel model){
        return ""
    }

    @Override
    def processVerificationOfSubmission(boolean verified) {
        if(verified){
            //todo fill template
            manager.downloadSubmission()
            output.displayStepsAfterSubmission(repository.subsequentSteps)
            //todo start download
        }
        return null
    }


}