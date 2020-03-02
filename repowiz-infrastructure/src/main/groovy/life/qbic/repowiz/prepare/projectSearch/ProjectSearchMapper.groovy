package life.qbic.repowiz.prepare.projectSearch

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.asapi.v3.dto.common.search.SearchResult
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
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.fetchoptions.DataSetFileFetchOptions
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.search.DataSetFileSearchCriteria

import life.qbic.dataLoading.PostmanDataFilterer
import life.qbic.dataLoading.PostmanDataFinder
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import life.qbic.xml.manager.StudyXMLParser
import life.qbic.xml.properties.Property
import life.qbic.xml.study.Qexperiment
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException


class ProjectSearchMapper implements ProjectSearchInput {

    private static final Logger LOG = LogManager.getLogger(ProjectSearchMapper.class)


    Project project
    HashMap mappingDB

    IApplicationServerApi v3
    IDataStoreServerApi dss
    String sessionToken

    //output
    ProjectSearchOutput output
    RepoWizProject repoWizProject

    //condition parsing
    StudyXMLParser studyParser = new StudyXMLParser()
    JAXBElement<Qexperiment> expDesign


    ProjectSearchMapper(IApplicationServerApi v3, IDataStoreServerApi dss, String session) {
        this.v3 = v3
        this.dss = dss

        sessionToken = session

        TemporaryDatabase temp = new TemporaryDatabase()
        mappingDB = temp.openBisToRepoWiz
    }

    def addProjectSearchOutput(ProjectSearchOutput out) {
        output = out
    }

    @Override
    def loadProjectInformation(String projectID) {
        loadOpenBisProjectInfo(projectID)

    }

    def loadOpenBisProjectInfo(String projectID) {
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
        HashMap projectData = new HashMap()
        String repoKey = mappingDB.get("Q_PROJECT_DETAILS")
        String value = project.description
        println project.properties
        projectData.put(repoKey, value)
        repoWizProject = new RepoWizProject(projectID, projectData)

        //prepare condition parse for samples
        project.experiments.each {
            if(it.type.code == "Q_PROJECT_DETAILS") {
                conditionParser(it.properties)
            }
        }
    }

    List<Property> getSampleCondition(String sample){
        studyParser.getFactorsAndPropertiesForSampleCode(expDesign,sample)
    }

    def conditionParser(Map experimentalDesign){

        String xmlString = experimentalDesign.get("Q_EXPERIMENTAL_SETUP")

            try {
                LOG.info "Parsing experiment conditions"
                expDesign = studyParser.parseXMLString(xmlString)
            }
            catch (JAXBException e) {
                LOG.info "Could not create new experimental design xml from experiment."
                //e.printStackTrace()
            }
    }

    //load the RepoWiz sample info
    def loadOpenBisSampleInfo() { //do that for a experiment or generally?? Experiment experiment
        LOG.info "Fetching Sample Information "
        output.userNotification("Fetching Sample Information ...")

        SampleFetchOptions fetchOptions = new SampleFetchOptions()
        fetchOptions.withType()
        fetchOptions.withProject()
        fetchOptions.withSpace()
        fetchOptions.withProperties()
        fetchOptions.withExperiment().withProperties()
        fetchOptions.withExperiment().withProject()
        fetchOptions.withChildrenUsing(fetchOptions)
        fetchOptions.withParentsUsing(fetchOptions)

        SampleSearchCriteria sampleSearchCriteria = new SampleSearchCriteria()
        sampleSearchCriteria.withSpace().withCode().thatEquals(project.space.code)
        sampleSearchCriteria.withExperiment().withProject().withCode().thatEquals(project.code)
        sampleSearchCriteria.withType().withCode().thatEquals("Q_TEST_SAMPLE")

        SearchResult<Sample> samples = v3.searchSamples(sessionToken, sampleSearchCriteria, fetchOptions)

        int counter = 1
        samples.objects.each { sample ->
            repoWizProject.addSample(new RepoWizSample("Sample "+counter, collectProperties(sample)))
            counter ++
        }
    }

    def collectProperties(Sample sample){
        HashMap allProperties = new HashMap()
        allProperties << fetchParentSamples(sample)
        allProperties << fetchChildSamples(sample)

        //just load the dataset for the current sample //todo can there be a sample higher than the q_test_sample??
        allProperties.put("rawFiles",loadOpenBisDataSetInfo(sample.code, "fastq"))

        allProperties << maskDuplicateProperties(sample)

        //add conditions
        if(expDesign != null){
            def res = getSampleCondition(sample.code)
            if (res != null) {
                res.each { sampleProp ->
                    String value = sampleProp.value
                    String label = sampleProp.label
                    allProperties.put("condition:" + label, value)

                    if (allProperties.containsKey("condition:" + label)) LOG.error("the condition $label is assigned to times to this sample $sample.code")
                }
            }
        }
        return allProperties
    }

    //load the RepoWiz data set info
    def loadOpenBisDataSetInfo(String sampleCode, String type) {
        LOG.info "Fetching Data Set ..."

        output.userNotification("Fetching Data Set ...")

        PostmanDataFinder finder = new PostmanDataFinder(v3, dss, new PostmanDataFilterer(), sessionToken)

        List<String> dataFiles = []

        finder.findAllDatasetsRecursive(sampleCode).each {
            DataSetFileSearchCriteria criteria = new DataSetFileSearchCriteria()
            criteria.withDataSet().withPermId().thatEquals(it.permId.permId)

            SearchResult<DataSetFile> result = dss.searchFiles(sessionToken, criteria, new DataSetFileFetchOptions())
            List<DataSetFile> files = result.getObjects()

            for (DataSetFile file : files) {
                if (file.getPermId().toString().contains("." + type)
                        && !file.getPermId().toString().contains(".sha256sum")
                        && !file.getPermId().toString().contains("origlabfilename")) {
                    String[] path = file.getPermId().toString().split("/")
                    dataFiles << path[path.size() - 1]
                }
            }

        }
        return dataFiles
    }

    HashMap fetchChildSamples(Sample sample) {
        HashMap childProperties = new HashMap()
        sample.children.each {child ->
            childProperties << getSampleCondition(child.code) //what if duplicates? for samples
            childProperties << experimentalProperties(child)
            childProperties << maskDuplicateProperties(child)
            childProperties << fetchChildSamples(child)
        }
        return childProperties
    }

    HashMap fetchParentSamples(Sample sample){
        HashMap parentProperties = new HashMap()
        sample.parents.each {parent ->
            parentProperties << getSampleCondition(parent.code) //keep that?
            parentProperties << experimentalProperties(parent)
            parentProperties << maskDuplicateProperties(parent)
            parentProperties << fetchParentSamples(parent)
        }
        return parentProperties
    }

    def experimentalProperties(Sample sample){
        maskDuplicateProperties(sample.experiment)
    }

    HashMap maskDuplicateProperties(Sample sample) {
        HashMap masked = new HashMap()

        sample.properties.each { key, value ->
            if (key == "Q_SECONDARY_NAME") masked.put("Q_SECONDARY_NAME_" + sample.type.code, value)
            else {
                masked.put(key, value)
            }
        }
        return masked
    }

    HashMap maskDuplicateProperties(Experiment exp) {
        HashMap masked = new HashMap()

        exp.properties.each { key, value ->
            if (key == "Q_SECONDARY_NAME") masked.put("Q_ADDITIONAL_INFO_" + exp.type.code, value)
            else {
                masked.put(key, value)
            }
        }
        return masked
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
