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

        TargetRepositoryProvider provider = targetRepository.provider(repository.repositoryName)
        provider.setUploadType(submission.uploadType)

        manager = provider.create()
        //output.displayInformation("Validate submission data")
        List<String> missingFields = manager.validateSubmissionModel(submission)

        //tell the user which fields are missing
        output.displayUserInformation("Following required fields are missing in your submission:")
        output.displayUserInformation(missingFields)
        //show the user the submission summary and ask him to verify the download
        output.displayUserInformation("Submission Summary:")
        output.displayUserInformation(manager.getSubmissionSummary())
        output.displayUserInformation("Is the displayed submission valid?")
        output.verifySubmission(["yes","no"])
    }

    @Override
    def processVerificationOfSubmission(boolean verified) {
        println "i verified the process"
        println verified

        if(verified){
            //todo fill template
            output.displayUserInformation("You successfully verified the submission")
            output.displayUserInformation("The download is prepared")
            manager.downloadSubmission()
            output.displayUserInformation(repository.subsequentSteps)
        }

        output.displayUserInformation("The submission was not verified. Please adjust your data and restart RepoWiz.")

        System.exit(1)
        return null
    }


}