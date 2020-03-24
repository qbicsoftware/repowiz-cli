package life.qbic.repowiz.prepare.openBis

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.asapi.v3.dto.common.search.SearchResult
import ch.ethz.sis.openbis.generic.asapi.v3.dto.dataset.DataSet
import ch.ethz.sis.openbis.generic.asapi.v3.dto.dataset.fetchoptions.DataSetFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.experiment.fetchoptions.ExperimentFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.Project
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.fetchoptions.ProjectFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.project.search.ProjectSearchCriteria
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.Sample
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.fetchoptions.SampleFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.search.SampleSearchCriteria
import ch.ethz.sis.openbis.generic.asapi.v3.dto.vocabulary.VocabularyTerm
import ch.ethz.sis.openbis.generic.asapi.v3.dto.vocabulary.fetchoptions.VocabularyTermFetchOptions
import ch.ethz.sis.openbis.generic.asapi.v3.dto.vocabulary.search.VocabularyTermSearchCriteria
import ch.ethz.sis.openbis.generic.dssapi.v3.IDataStoreServerApi
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.DataSetFile
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.fetchoptions.DataSetFileFetchOptions
import ch.ethz.sis.openbis.generic.dssapi.v3.dto.datasetfile.search.DataSetFileSearchCriteria


import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchInput
import life.qbic.repowiz.prepare.projectSearch.ProjectSearchOutput
import life.qbic.xml.properties.Property
import org.apache.commons.lang3.StringUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

//todo separate code, try to create general class for plugin architecture and move openBis code into concrete opnebis class
class ProjectSearcher implements ProjectSearchInput {

    private static final Logger LOG = LogManager.getLogger(ProjectSearcher.class)

    Project project

    IApplicationServerApi v3
    IDataStoreServerApi dss
    String sessionToken

    //output
    ProjectSearchOutput output
    RepoWizProject repoWizProject
    List repoWizSamples = []

    OpenBisMapper mapper = new OpenBisMapper()

    ConditionParser conditionParser

    ProjectSearcher(IApplicationServerApi v3, IDataStoreServerApi dss, String session) {
        this.v3 = v3
        this.dss = dss

        sessionToken = session
    }

    def addProjectSearchOutput(ProjectSearchOutput out) {
        output = out
    }

    @Override
    def loadProjectInformation(String projectID) {
        loadOpenBisProjectInfo(projectID)
        loadOpenBisSampleInfo()

        output.transferProjectData(repoWizProject,repoWizSamples)
    }

    def loadOpenBisProjectInfo(String projectID) {
        LOG.info "Fetching Project Information ..."

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


        //todo how to get rid of code here?
        repoWizProject = new RepoWizProject(projectID, mapper.mapProperties(["Q_PROJECT_DETAILS":project.description]))

        //prepare condition parse for samples
        project.experiments.each {exp ->
            if(exp.type.code == "Q_PROJECT_DETAILS"){
                conditionParser = new ConditionParser(exp.properties)
                conditionParser.parse()
            }
        }
    }

    //load the RepoWiz sample info
    def loadOpenBisSampleInfo() { //do that for a experiment or generally?? Experiment experiment
        LOG.info "Fetching Sample Information ..."
        output.userNotification("Fetching Sample Information ...")

        SampleFetchOptions fetchOptions = new SampleFetchOptions()
        fetchOptions.withType()
        fetchOptions.withProject()
        fetchOptions.withSpace()
        fetchOptions.withProperties()
        fetchOptions.withExperiment().withType()
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
            //todo mapp the terms before adding to project!!!!!
            HashMap sampleProperties = collectProperties(sample)

            repoWizSamples << new RepoWizSample("Sample "+counter, sampleProperties)
            counter ++
        }

    }

    def collectProperties(Sample sample){
        HashMap<String,String> allProperties = new HashMap()

        //todo mask openbis terms tissue, ncbi organism

        allProperties << fetchParentSamples(sample)
        allProperties << fetchChildSamples(sample)

        //just load the dataset for the current sample //todo can there be a sample higher than the q_test_sample??
        LOG.info "Fetching Data Set ..."
        allProperties << loadOpenBisDataSetInfo(sample.code, "fastq")

        allProperties << mapper.mapProperties(mapper.maskDuplicateProperties(sample.type.code,sample.properties))

        //add conditions
        List<Property> res = conditionParser.getSampleCondition(sample.code)
        allProperties << mapper.mapConditions(res)

        //map openBis vocabulary to readable names
        allProperties.each {key, value ->
            if (StringUtils.isNumeric(value.toString())) getVocabulary(value.toString())
        }

        return allProperties
    }

    //load the RepoWiz data set info
    HashMap loadOpenBisDataSetInfo(String sampleCode, String type) {

        HashMap allDataSets = new HashMap()

        findAllDatasetsRecursive(sampleCode).each { dataSet ->
            DataSetFileSearchCriteria criteria = new DataSetFileSearchCriteria()
            criteria.withDataSet().withPermId().thatEquals(dataSet.permId.permId)

            SearchResult<DataSetFile> result = dss.searchFiles(sessionToken, criteria, new DataSetFileFetchOptions())
            List<DataSetFile> files = result.getObjects()

            List<String> dataFiles = []

            for (DataSetFile file : files) {
                if (file.getPermId().toString().contains("." + type)
                        && !file.getPermId().toString().contains(".sha256sum")
                        && !file.getPermId().toString().contains("origlabfilename")) {
                    String[] path = file.getPermId().toString().split("/")
                    dataFiles << path[path.size() - 1]
                }
            }
            allDataSets << mapper.mapFiles(dataFiles, dataSet.type.code)
        }
        return allDataSets
    }

    List<DataSet> findAllDatasetsRecursive(final String sampleId) {
        SampleSearchCriteria criteria = new SampleSearchCriteria()
        criteria.withCode().thatEquals(sampleId)

        // tell the API to fetch all descendants for each returned sample
        SampleFetchOptions fetchOptions = new SampleFetchOptions()
        DataSetFetchOptions dsFetchOptions = new DataSetFetchOptions()
        dsFetchOptions.withType()
        fetchOptions.withChildrenUsing(fetchOptions)
        fetchOptions.withDataSetsUsing(dsFetchOptions)
        SearchResult<Sample> result = v3.searchSamples(sessionToken, criteria, fetchOptions)

        List<DataSet> foundDatasets = new ArrayList<>()

        for (Sample sample : result.getObjects()) {
            // add the datasets of the sample itself
            foundDatasets.addAll(sample.getDataSets())

            // fetch all datasets of the children
            foundDatasets.addAll(fetchDescendantDatasets(sample))
        }

        return foundDatasets
    }

    private static List<DataSet> fetchDescendantDatasets(final Sample sample) {
        List<DataSet> foundSets = new ArrayList<>()

        // fetch all datasets of the children recursively
        for (Sample child : sample.getChildren()) {
            final List<DataSet> foundChildrenDatasets = child.getDataSets()
            foundSets.addAll(foundChildrenDatasets)
            foundSets.addAll(fetchDescendantDatasets(child))
        }

        return foundSets
    }


    HashMap fetchChildSamples(Sample sample) {
        HashMap childProperties = new HashMap()
        sample.children.each {child ->
            childProperties << mapper.mapConditions(conditionParser.getSampleCondition(child.code))
            childProperties << mapper.mapProperties(mapper.maskDuplicateProperties(child.type.code, child.properties))
            childProperties << mapper.mapProperties(mapper.maskDuplicateProperties(child.experiment.type.code, child.experiment.properties))
            childProperties << fetchChildSamples(child)
        }
        return childProperties
    }

    HashMap fetchParentSamples(Sample sample){
        HashMap parentProperties = new HashMap()
        sample.parents.each {parent ->
            parentProperties << mapper.mapConditions(conditionParser.getSampleCondition(parent.code))
            parentProperties << mapper.mapProperties(mapper.maskDuplicateProperties(parent.type.code, parent.properties))
            parentProperties << mapper.mapProperties(mapper.maskDuplicateProperties(parent.experiment.type.code, parent.experiment.properties))
            parentProperties << fetchParentSamples(parent)
        }
        return parentProperties
    }


    def getVocabulary(String code){
        VocabularyTermSearchCriteria vocabularyTermSearchCriteria = new VocabularyTermSearchCriteria()
        vocabularyTermSearchCriteria.withCode().thatEquals(code)

        SearchResult<VocabularyTerm> vocabularyTermSearchResult = v3.searchVocabularyTerms(sessionToken, vocabularyTermSearchCriteria, new VocabularyTermFetchOptions())

        String term =  vocabularyTermSearchResult.objects.get(0).label

        return term
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
