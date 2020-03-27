package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider
import life.qbic.repowiz.model.SubmissionModel
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
        List missingFields = manager.validateSubmissionModel(submission)

        //tell the user which fields are missing
        output.displayUserInformation("Following required fields are missing in your submission:")
        output.displayUserInformation(missingFields)
        //show the user the submission summary and ask him to verify the download
        output.displayUserInformation("Submission Summary:")
        output.displayUserInformation(manager.getSubmissionSummary())
        output.verifySubmission()
    }

    @Override
    def processVerificationOfSubmission(boolean verified) {
        if(verified){
            //todo fill template
            manager.downloadSubmission()
            output.displayUserInformation(repository.subsequentSteps)
        }
        return null
    }


}