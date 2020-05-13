package spiimpl

import life.qbic.repowiz.model.RepoWizProject
import life.qbic.repowiz.model.RepoWizSample
import life.qbic.repowiz.model.SubmissionModel
import life.qbic.repowiz.spiimpl.GeoSubmissionManager
import spock.lang.Specification

class GeoSubmissionManagerSpecification extends Specification{

    def "maps geoTerm to correct repoWizTerm"(){
        given:
        GeoSubmissionManager manager = new GeoSubmissionManager()
        HashMap properties = ["project title":"value", "raw file name":"value2"]

        when:
        def res = manager.mapProperties(properties,"sample 1")

        then:
        res.sort() == ["series_title":"value", "raw files_file name":"value2"].sort()
    }

    def "maps repoWizTerm submission model to geoTerm submission model"(){
        given:
        GeoSubmissionManager manager = new GeoSubmissionManager()
        HashMap projectProp = ["project title":"value"]
        RepoWizProject project = new RepoWizProject("projectid",projectProp)

        HashMap sampleProp = ["sample name":"value", "raw file":["file1","file2"]]
        RepoWizSample sample = new RepoWizSample("sample 1",sampleProp)

        SubmissionModel model = new SubmissionModel(project,[sample])

        HashMap projectProp2 = ["series_title":"value"]
        RepoWizProject project2 = new RepoWizProject("projectid",projectProp2)

        HashMap sampleProp2 = ["samples_Sample name":"value", "samples_raw file":["file1","file2"]]
        RepoWizSample sample2 = new RepoWizSample("sample 2",sampleProp2)

        SubmissionModel model2 = new SubmissionModel(project2,[sample2])

        when:
        manager.mapMetadata(model)
        SubmissionModel res = manager.getProviderSubmissionModel()

        then:
        res.samples.get(0).properties.sort() == model2.samples.get(0).properties.sort()
        assert res.project.properties.sort() == model2.project.properties.sort()
    }


    def "map characteristics"(){
        given:
        GeoSubmissionManager manager = new GeoSubmissionManager()

        HashMap properties = ["organism":"Homo sapiens","characteristic genotype":"wildtype",
                              "characteristic treatment":"non"]

        when:
        manager.mapProperties(properties,"name1")

        then:
        manager.getCharacteristics() == ["name1":["characteristics: treatment":"non","characteristics: genotype":"wildtype"]]

    }
}
