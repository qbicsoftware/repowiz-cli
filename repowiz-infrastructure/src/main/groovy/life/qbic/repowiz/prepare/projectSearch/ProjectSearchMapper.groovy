package life.qbic.repowiz.prepare.projectSearch

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.asapi.v3.dto.common.search.SearchResult
import ch.ethz.sis.openbis.generic.asapi.v3.dto.dataset.DataSet
import ch.ethz.sis.openbis.generic.asapi.v3.dto.dataset.fetchoptions.DataSetFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.dataset.id.DataSetPermId
import ch.ethz.sis.openbis.generic.asapi.v3.dto.experiment.Experiment
import ch.ethz.sis.openbis.generic.asapi.v3.dto.experiment.fetchoptions.ExperimentFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.Project
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.fetchoptions.ProjectFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.search.ProjectSearchCriteria
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.Sample
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.fetchoptions.SampleFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.search.SampleSearchCriteria
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.DataSetFile
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.download.DataSetFileDownloadOptions
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.fetchoptions.DataSetFileFetchOptions
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.id.DataSetFilePermId
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.id.IDataSetFileId
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.search.DataSetFileSearchCriteria
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class ProjectSearchMapper implements ProjectSearchInput{

    private static final Logger LOG = LogManager.getLogger(ProjectSearchMapper.class)


    Project project
    HashMap mappingDB

    IApplicationServerApi v3
    IDataStoreServerApi dss
    String sessionToken

    ProjectSearchOutput output
    HashMap projectData = new HashMap()


    ProjectSearchMapper(IApplicationServerApi v3, IDataStoreServerApi dss, String session){
        this.v3 = v3
        this.dss = dss

        sessionToken = session

        TemporaryDatabase temp = new TemporaryDatabase()
        mappingDB = temp.openBisToRepoWiz
    }

    def addProjectSearchOutput(ProjectSearchOutput out){
        output = out
    }

    @Override
    def loadProjectInformation(String projectID){
        loadOpenBisProjectInfo(projectID)

    }

    def loadOpenBisProjectInfo(String projectID){
        LOG.info "Fetching Project Information "

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

        checkSpaceAvailability()


        //todo get rid of this mapping code in this class
        //this needs to be done for samples --> on sample level
        String repoKey = mappingDB.get("Q_PROJECT_DETAILS")
        String value = project.description
        projectData.put(repoKey,value)
    }

    //load the RepoWiz experiment info
    def loadOpenBisExperimentInfo(){
        LOG.info "Fetching Experiment Information "
        println "fetch experiment"

        //output.userNotification("Fetching Experiment Information ... ")
        project.getExperiments().each {experiment ->
            LOG.debug experiment.type.code + " is experiment type"

            experiment.properties.each {key, value ->
                if(key == "Q_ADDITIONAL_INFO"){
                    String repoKey = mappingDB.get("Q_ADDITIONAL_INFO_EXPERIMENT")
                    //todo parse that?
                    projectData.put(repoKey,value)
                }
                else if(key == "Q_EXPERIMENTAL_SETUP"){
                    //todo parse
                    //todo handle multiple conditions
                    //need sample in order to map the condition value to the sample
                    projectData.put("condition???",value)
                }
                else{
                    String repoKey = mappingDB.get(key)
                    projectData.put(repoKey,value)
                }

            }
        }

    }

    //load the RepoWiz sample info
    def loadOpenBisSampleInfo(){ //do that for a experiment or generally?? Experiment experiment
        LOG.info "Fetching Sample Information "
        println "fetch sample"

        output.userNotification("Fetching Sample Information ...")

        //todo mask secondary_name!!!!

        SampleFetchOptions fetchOptions = new SampleFetchOptions()
        fetchOptions.withType()
        fetchOptions.withProject()
        fetchOptions.withSpace()
        fetchOptions.withProperties()
        fetchOptions.withExperiment().withProperties()
        fetchOptions.withExperiment().withProject()
        fetchOptions.withChildrenUsing(fetchOptions)

        DataSetFetchOptions dataSetFetchOptions = new DataSetFetchOptions()
        dataSetFetchOptions.withProperties()
        dataSetFetchOptions.withType()
        dataSetFetchOptions.withChildrenUsing(dataSetFetchOptions)

        fetchOptions.withDataSetsUsing(dataSetFetchOptions)

        SampleSearchCriteria sampleSearchCriteria = new SampleSearchCriteria()
        sampleSearchCriteria.withSpace().withCode().thatEquals(project.space.code)
        sampleSearchCriteria.withExperiment().withProject().withCode().thatEquals(project.code)

        SearchResult<Sample> samples = v3.searchSamples(sessionToken, sampleSearchCriteria, fetchOptions)

        samples.objects.each { sample ->
            if (sample.dataSets != null){
                println sample.dataSets.size() + " number of datasets"
                loadOpenBisDataSetInfo(sample)
            }
            sample.properties
            //todo handle properties that occur multiple times

        }
    }

    //load the RepoWiz data set info
    def loadOpenBisDataSetInfo(Sample sample){
        LOG.info "Fetching Data Files and Information "
        //todo do recursive search!

        output.userNotification("Fetching Data Sets")

        sample.dataSets.each {
            println it.type.code
            println sample.type

            DataSetFileSearchCriteria criteria = new DataSetFileSearchCriteria()
            criteria.withDataSet().withSample().withCode().thatEquals(sample.getCode())

            SearchResult<DataSetFile> result = dss.searchFiles(sessionToken, criteria, new DataSetFileFetchOptions())
            List<DataSetFile> files = result.getObjects()

            for (DataSetFile file : files)
            {
                System.out.println(file.getPath() + " " + file.getFileLength())
                String[] path = file.getPermId().toString().split("/")
                println path
                println path[path.size() - 1]
            }

            /*DataSetFileSearchCriteria criteria = new DataSetFileSearchCriteria();
            criteria.withDataSet().withSample().withCode().thatEquals(rawDataSample.getCode());
            SearchResult<DataSetFile> files = dss
                    .searchFiles(sessionToken, criteria, new DataSetFileFetchOptions());
            for (DataSetFile file : files.getObjects())
                if (file.getPermId().toString().contains(".fastq")
                        && !file.getPermId().toString().contains(".sha256sum")
                        && !file.getPermId().toString().contains("origlabfilename")) {
                    SampleGEO geo = new SampleGEO(false);
                    geo.setSampleName("Sample " + (i + 1));
                    geo.setCode("Code: " + rawDataSample.getCode());

                    String[] path = file.getPermId().toString().split("/");*/

        }


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

    private void checkSpaceAvailability() {

        if (project == null) {
            output.userNotification("Project " + project.code + " does not exist for user")
            LOG.error "Project " + project.code + " does not exist for user"
            v3.logout(sessionToken)
            System.exit(0)
        } else {
            output.userNotification("Found project " + project.code + " for user")
            LOG.info "Found project " + project.code + " for user"
        }
    }
}
