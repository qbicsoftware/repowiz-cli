package life.qbic.repowiz.prepare.projectSearch

import life.qbic.repowiz.io.JsonParser
import life.qbic.repowiz.model.RepoWizProject

abstract class ProjectSearcher implements ProjectSearchInput{

    Mapper mapper
    String projectSchema
    String sampleSchema

    //output
    ProjectSearchOutput output
    RepoWizProject repoWizProject
    List repoWizSamples = []

    def addProjectSearchOutput(ProjectSearchOutput out) {
        output = out
    }

    def createModel(){
        output.createSubmissionModel(repoWizProject,repoWizSamples)
    }

    def sampleValidation(Map sampleProperties){
        JsonParser.validate(sampleSchema,sampleProperties)
    }

    def projectValidation(Map projectProperties){
        JsonParser.validate(projectSchema,projectProperties)
    }


}
