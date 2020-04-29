package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.finalise.spi.TargetRepositoryProvider
import life.qbic.repowiz.model.SubmissionModel
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.sql.Timestamp
import java.text.SimpleDateFormat

class FinaliseSubmissionImpl implements FinaliseSubmission{

    SubmissionOutput output
    TargetRepository targetRepository
    Repository repository
    SubmissionManager manager

    private static final Logger LOG = LogManager.getLogger(FinaliseSubmissionImpl.class)

    String home = System.getProperty("user.home")
    String file = home+"/Downloads/" + "repoWiz_submission"

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss")

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

        //show the user the submission summary
        output.displayUserInformation("Submission Summary:")
        createSummary()

        //validate submission
        output.displayUserInformation("Is the displayed submission valid?")
        output.verifySubmission(["yes","no"])
    }

    @Override
    def processVerificationOfSubmission(boolean verified) {

        if(verified){
            output.displayUserInformation("You successfully verified the submission")
            LOG.info("The download is prepared ...")
            Timestamp time = new Timestamp(System.currentTimeMillis())

            String projectID = manager.providerSubmissionModel.project.projectID
            manager.downloadSubmission(file+ "-"+projectID +"-" +sdf.format(time))

            output.displayUserInformation("Further information: ")
            output.displayUserInformation(repository.subsequentSteps)
            System.exit(1)
        }

        output.displayUserInformation("The submission was not verified. Please adjust your data and restart RepoWiz.")

        System.exit(1)
        return null
    }

    def createSummary(){
        SubmissionModel model = manager.providerSubmissionModel

        //project
        HashMap projectProperties = model.project.properties
        String projectID = model.project.projectID

        output.displayProjectSummary(projectProperties,projectID)

        //samples
        HashMap<String,HashMap> sampleProperties = new HashMap()
        model.samples.each {sample ->
            sampleProperties.put(sample.sampleName, sample.properties)
        }

        output.displaySampleSummary(sampleProperties)
    }


}