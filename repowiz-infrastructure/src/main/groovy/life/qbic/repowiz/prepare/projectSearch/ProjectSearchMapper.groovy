package life.qbic.repowiz.prepare.projectSearch

import ch.ethz.sis.openbis.generic.asapi.v3.IApplicationServerApi
import ch.ethz.sis.openbis.generic.asapi.v3.dto.common.search.SearchResult
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

import life.qbic.dataLoading.PostmanDataFilterer
import life.qbic.dataLoading.PostmanDataFinder
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import life.qbic.xml.manager.StudyXMLParser
import life.qbic.xml.properties.Property
import life.qbic.xml.study.Qexperiment
import org.apache.commons.lang3.StringUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException


class ProjectSearchMapper implements ProjectSearchInput {

    private static final Logger LOG = LogManager.getLogger(ProjectSearchMapper.class)

    Project project

    IApplicationServerApi v3
    IDataStoreServerApi dss
    String sessionToken

    //output
    ProjectSearchOutput output
    RepoWizProject repoWizProject
    Mapp mapper = new Mapp()

    ConditionParser conditionParser

    ProjectSearchMapper(IApplicationServerApi v3, IDataStoreServerApi dss, String session) {
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

        output.transferProjectMetadata(repoWizProject)
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
        repoWizProject = new RepoWizProject(projectID, mapper.maskProperties(["Q_PROJECT_DETAILS":project.description]))

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

            repoWizProject.addSample(new RepoWizSample("Sample "+counter, sampleProperties))
            counter ++
        }

    }

    def collectProperties(Sample sample){
        HashMap<String,String> allProperties = new HashMap()

        //todo mask openbis terms tissue, ncbi organism

        allProperties << fetchParentSamples(sample)
        allProperties << fetchChildSamples(sample)

        //just load the dataset for the current sample //todo can there be a sample higher than the q_test_sample??
        output.userNotification("Fetching Data Set ...")
        LOG.info "Fetching Data Set ..."
        allProperties << loadOpenBisDataSetInfo(sample.code, "fastq")

        allProperties << mapper.maskProperties(mapper.maskDuplicateProperties(sample.type.code,sample.properties))

        //add conditions
        List<Property> res = conditionParser.getSampleCondition(sample.code)
        allProperties << mapper.maskConditions(res)

        //map openBis vocabulary to readable names
        allProperties.each {key, value ->
            if (StringUtils.isNumeric(value.toString())) getVocabulary(value.toString())
        }

        return allProperties
    }

    //load the RepoWiz data set info
    HashMap loadOpenBisDataSetInfo(String sampleCode, String type) {

        PostmanDataFinder finder = new PostmanDataFinder(v3, dss, new PostmanDataFilterer(), sessionToken)

        HashMap allDataSets = new HashMap()

        finder.findAllDatasetsRecursive(sampleCode).each { dataSet ->
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
            allDataSets << mapper.maskFiles(dataFiles, dataSet.type.code)
        }
        return allDataSets
    }

    HashMap fetchChildSamples(Sample sample) {
        HashMap childProperties = new HashMap()
        sample.children.each {child ->
            childProperties << mapper.maskConditions(conditionParser.getSampleCondition(child.code))
            childProperties << mapper.maskProperties(mapper.maskDuplicateProperties(child.type.code, child.properties))
            childProperties << mapper.maskProperties(mapper.maskDuplicateProperties(child.experiment.type.code, child.experiment.properties))
            childProperties << fetchChildSamples(child)
        }
        return childProperties
    }

    HashMap fetchParentSamples(Sample sample){
        HashMap parentProperties = new HashMap()
        sample.parents.each {parent ->
            parentProperties << mapper.maskConditions(conditionParser.getSampleCondition(parent.code))
            parentProperties << mapper.maskProperties(mapper.maskDuplicateProperties(parent.type.code, parent.properties))
            parentProperties << mapper.maskProperties(mapper.maskDuplicateProperties(parent.experiment.type.code, parent.experiment.properties))
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
