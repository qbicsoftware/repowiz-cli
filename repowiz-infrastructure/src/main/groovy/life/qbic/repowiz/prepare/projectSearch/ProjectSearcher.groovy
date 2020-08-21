package life.qbic.repowiz.prepare.projectSearch

import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.model.RepoWizProject

/**
 * Class to define how a project is searched. Each local database that provides data for RepoWiz must extend this class!
 *
 * This class should be used whenever submission data from a database needs to be provided for RepoWiz. It allows the handle
 * the project search in this database and separates the database logic from the rest of the code.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
abstract class ProjectSearcher implements ProjectSearchInput {

    protected Mapper mapper
    protected String projectSchema
    protected String sampleSchema

    //output
    protected ProjectSearchOutput output
    protected RepoWizProject repoWizProject
    protected List repoWizSamples = []

    /**
     * Defines where the data from the implementing class is delegated to
     *
     * @param out is the implementation of the {@link ProjectSearchOutput}. It allows to inject data into the repowiz-domain and the use cases
     */
    void addProjectSearchOutput(ProjectSearchOutput out) {
        output = out
    }

    /**
     * Creates a submission model from a RepoWizProject and a RepoWizSample
     */
    void createModel() {
        output.createSubmissionModel(repoWizProject, repoWizSamples)
    }

    /**
     * Validates the a map with sample properties with a given sample schema
     *
     * @param sampleProperties which describe the properties of a sample, the properties are already defined as RepoWiz terms
     */
    void sampleValidation(Map sampleProperties) {
        JsonParser.validate(sampleSchema, sampleProperties)
    }

    /**
     * Validates the project properties with a given project schema
     *
     * @param projectProperties which describe the properties of a project, the properties are already defined as RepoWiz terms
     */
    void projectValidation(Map projectProperties) {
        JsonParser.validate(projectSchema, projectProperties)
    }
}
