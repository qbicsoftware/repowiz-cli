package life.qbic.repowiz.finalise

import life.qbic.repowiz.Repository
import life.qbic.repowiz.model.SubmissionModel
import life.qbic.repowiz.spi.SubmissionManager
import life.qbic.repowiz.spi.TargetRepository
import life.qbic.repowiz.spi.TargetRepositoryProvider
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import java.sql.Timestamp
import java.text.SimpleDateFormat

/**
 * This class finalizes submissions.
 *
 * This class implements the use case FinaliseSubmission. During finalization, whenever a submission created by RepoWiz is transferred to a repository plugin, this class should be used. 
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */

class FinaliseSubmissionImpl implements FinaliseSubmission {

    private final SubmissionOutput output
    private final TargetRepository targetRepository
    private Repository repository
    private SubmissionManager manager

    private static final Logger LOG = LogManager.getLogger(FinaliseSubmissionImpl.class)

    private final String home = System.getProperty("user.home")
    private final String file = home + "/Downloads/" + "repoWiz_submission"

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss")

    /**
     * Creates the FinaliseSubmission use case which gets access to a target repository and to a submission output
     * @param targetRepository contains information about the repository
     * @param out determines how data is transferred out of this class
     */
    FinaliseSubmissionImpl(TargetRepository targetRepository, SubmissionOutput out) {
        this.targetRepository = targetRepository
        output = out
    }

    @Override
    void transferSubmissionData(SubmissionModel submission, Repository repository) {
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

        output.displayUserInformation("Is the displayed submission valid?")
        output.verifySubmission(["yes", "no"])
    }

    @Override
    void processVerificationOfSubmission(boolean verified) {

        if (verified) {
            output.displayUserInformation("You successfully verified the submission")
            LOG.info("The download is prepared ...")
            Timestamp time = new Timestamp(System.currentTimeMillis())

            String projectID = manager.providerSubmissionModel.project.projectID
            manager.downloadSubmission(file + "-" + projectID + "-" + sdf.format(time))

            output.displayUserInformation("Further information: ")
            output.displayUserInformation(repository.subsequentSteps)
            System.exit(0)
        }

        output.displayUserInformation("The submission was not verified. Please adjust your data and restart RepoWiz.")

        System.exit(1)
    }

    /**
     * Creates a summary of the submitted project and its samples
     */
    void createSummary() {
        SubmissionModel model = manager.providerSubmissionModel

        //project
        Map projectProperties = model.project.projectProperties as Map
        String projectID = model.project.projectID

        output.displayProjectSummary(projectProperties, projectID)

        //samples
        Map<String, Map> sampleProperties = new HashMap()
        model.samples.each { sample ->
            HashMap<String, String> properties = new HashMap<>()

            sample.sampleProperties.each { property ->
                properties.put(property.key.toString(), property.value.toString())
            }

            sampleProperties.put(sample.sampleName, properties)
        }

        output.displaySampleSummary(sampleProperties)
    }


}
