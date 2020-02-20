package life.qbic.repowiz.prepare.projectSearch

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.asapi.v3.dto.common.search.SearchResult
import ch.ethz.sis.openbis.generic.asapi.v3.dto.dataset.fetchoptions.DataSetFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.experiment.Experiment
import ch.ethz.sis.openbis.generic.asapi.v3.dto.experiment.fetchoptions.ExperimentFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.Project
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.fetchoptions.ProjectFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.search.ProjectSearchCriteria
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.Sample
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.fetchoptions.SampleFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.search.SampleSearchCriteria

class ProjectSearchMapper implements ProjectSearchInput{

    Project project
    String sampleConditions
    //safe her project info/repowiz data types?
    //need mapper?

    IApplicationServerApi v3
    String sessionToken

    ProjectSearchOutput output


    ProjectSearchMapper(IApplicationServerApi v3, String session){
        this.v3 = v3
        sessionToken = session

    }

    def addProjectSearchOutput(ProjectSearchOutput out){
        output = out
    }

    /*
    https://github.com/qbicsoftware/projectbrowser-portlet/blob/master/src/main/java/life/qbic/projectbrowser/controllers/DataHandler.java#L694
    (kannst entweder direkt nach dem experiment suchen oder wenn du eh alle durchgehst den code vergleichen)
    https://github.com/qbicsoftware/projectbrowser-portlet/blob/master/src/main/java/life/qbic/projectbrowser/controllers/DataHandler.java#L726
    (parsen der xml property und dann weiter unten welche samples zu welcher gruppe gehören)
     */

    @Override
    def loadProject(String projectID){
        // invoke other API methods using the session token, for instance search for spaces
        ProjectSearchCriteria projectSearchCriteria = new ProjectSearchCriteria()
        projectSearchCriteria.withCode().thatEquals(projectID)

        ProjectFetchOptions projectFetchOptions = new ProjectFetchOptions()
        projectFetchOptions.withSpace()
        projectFetchOptions.withExperiments()

        ExperimentFetchOptions experimentFetchOptions = new ExperimentFetchOptions()
        experimentFetchOptions.withProperties()
        experimentFetchOptions.withType()

        projectFetchOptions.withExperimentsUsing(experimentFetchOptions)

        SearchResult<Project> projects = v3.searchProjects(sessionToken, projectSearchCriteria, projectFetchOptions)
        project = projects.getObjects().get(0)

        checkSpaceAvailability(projectID)

        output.userNotification("Fetching Project Information ... ")

        project.getExperiments().each { experiment ->
            if(experiment.type.code == "Q_PROJECT_DETAILS"){
                sampleConditions = experiment.properties.get("Q_EXPERIMENTAL_SETUP")
                //todo parse the xml and assign to samples
                parseXmlConditions(sampleConditions)
            }
        }
    }

    def parseXmlConditions(String xml){

    }

    //load the RepoWiz experiment info
    def loadExperimentInfo(){
        output.userNotification("Fetching Experiment Information ... ")
        output.userNotification("There is currently no openBis information describing the conducted experiments")
        //currently there are no fields in openBis that describe the experiment fields

    }

    //load the RepoWiz sample info
    def loadSampleInfo(){ //do that for a experiment or generally?? Experiment experiment
        output.userNotification("Fetching Sample Information ...")

        SampleFetchOptions fetchOptions = new SampleFetchOptions()
        fetchOptions.withType()
        fetchOptions.withProject()
        fetchOptions.withSpace()
        fetchOptions.withProperties()
        fetchOptions.withExperiment().withProperties()
        fetchOptions.withExperiment().withProject()


        SampleSearchCriteria sampleSearchCriteria = new SampleSearchCriteria()
        sampleSearchCriteria.withSpace().withCode().thatEquals(project.space.code)
        sampleSearchCriteria.withExperiment().withProject().withCode().thatEquals(project.code)

        SearchResult<Sample> samples = v3.searchSamples(sessionToken, sampleSearchCriteria, fetchOptions)

        samples.objects.each { sample ->
            println sample.type
            //find all sample properties for one "sample" independent of sample types in openBIS
        }
    }

    //load the RepoWiz data set info
    def loadDataInfo(Sample sample){
        output.userNotification("Fetching Data Sets")

        /*SampleFetchOptions fetchOptions = new SampleFetchOptions()
        fetchOptions.withType()
        fetchOptions.withProject()
        fetchOptions.withSpace()
        fetchOptions.withProperties()
        fetchOptions.withExperiment().withProperties()
        fetchOptions.withExperiment().withProject()

        DataSetFetchOptions dataSetFetchOptions = new DataSetFetchOptions()
        dataSetFetchOptions.withExperiment()
        fetchOptions.withDataSetsUsing(dataSetFetchOptions)

        SampleSearchCriteria rawDataCriteria = new SampleSearchCriteria()
        rawDataCriteria.withSpace().withCode().thatEquals(project.space.code)
        rawDataCriteria.withExperiment().withProject().withCode().thatEquals(project.code)
        rawDataCriteria.withType().withCode().thatEquals("Q_NGS_SINGLE_SAMPLE_RUN")*/


    }

    private void checkSpaceAvailability(String projectCode) {

        if (project == null) {
            output.userNotification("Project " + projectCode + " does not exist for user")
            v3.logout(sessionToken)
            System.exit(0)
        } else {
            output.userNotification("Found project " + projectCode + " for user")
        }
    }
}
