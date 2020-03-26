package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.mapping.GeoMapper
import life.qbic.repowiz.model.RepoWizProject
import life.qbic.repowiz.model.RepoWizSample
import life.qbic.repowiz.model.SubmissionModel
import life.qbic.repowiz.submissionTypes.GeoHtsSubmission
import life.qbic.repowiz.submissionTypes.GeoSubmission
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class GeoSubmissionManager implements SubmissionManager{

    private SubmissionModel geoSubmissionModel
    private GeoMapper mapper
    private GeoSubmission geoSubmission

    private static final Logger LOG = LogManager.getLogger(GeoSubmissionManager.class)

    GeoSubmissionManager(){
        LOG.info "Creating Geo Submission Manager"
        mapper = new GeoMapper()
    }

    @Override
    SubmissionModel validateSubmissionModel(SubmissionModel model) {//idea load the upload/submission type from the model
        mapMetadata(model)
        //todo load all required fields for valid submission
        List missingFields = geoSubmission.determineMissingFields([])
        //todo answer with validation status and missing fields
        if (missingFields == []) LOG.info "The submission is valid, all required fields are defined"

        return model
    }

    @Override
    void downloadSubmission() {
        //todo download template for user
    }

    void createGeoSubmissionObject(String uploadType){

        switch (uploadType){
            case "hts": //defined in the repository description (json file)
                geoSubmission = new GeoHtsSubmission()
                break
            case "affymetrix_GE":
                //whole gene expression
                //todo add Class extending the GeoSubmission-Object with information about this type
                break
        }
    }

    //todo continue from here
    private void mapMetadata(SubmissionModel model){
        //map project data
        HashMap mappedProjectProps = model.project.properties
        RepoWizProject mappedProject = new RepoWizProject(model.project.projectID,new HashMap())
        //map sample data
        List<RepoWizSample> mappedSamples = []

        model.samples.each {sample ->
            /*HashMap mappedSampleProps = mapper.getGeoTerm(sample.properties)
            mappedSamples << new RepoWizSample(sample.sampleName,mappedSampleProps)*/
        }

        geoSubmissionModel = new SubmissionModel(mappedProject,mappedSamples)
    }

    private static List<String> getProperties(SubmissionModel model){
        List<String> properties = []

        //add project property attributes
        properties.addAll(model.project.properties.keySet())
        //add sample property attributes
        model.samples.each {sample ->
            sample.properties.keySet().each {property ->
                if (!properties.contains(property)) properties.addAll(property)
            }
        }
        return properties
    }

}
