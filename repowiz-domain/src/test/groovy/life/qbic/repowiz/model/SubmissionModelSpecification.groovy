package life.qbic.repowiz.model

import spock.lang.Specification

class SubmissionModelSpecification extends Specification{

    /*def "getRawFiles() returns list with sample names mapped to files"(){
        given:
        RepoWizSample sample = new RepoWizSample("name1",["ddd":"kkkk"])
        sample.rawFiles = ["file1.fasta","file2.fasta","file3.fasta"]
        RepoWizSample sample2 = new RepoWizSample("name2",["ddd":"kkkk"])
        sample2.rawFiles = ["file1.fasta","file2.fasta","file3.fasta"]
        //["raw file":["file1.fasta","file2.fasta","file3.fasta"]]
        RepoWizProject project = new RepoWizProject("id",["property":"prop"])

        SubmissionModel model = new SubmissionModel(project,[sample,sample2])

        when:
        def res = model.getAllRawFiles()

        then:
        res.sort() == ["name1":["file1.fasta","file2.fasta","file3.fasta"],"name2":["file1.fasta","file2.fasta","file3.fasta"]].sort()
    }

    def "getCharacteristics() returns list with sample names mapped to files"(){
        given:
        RepoWizSample sample = new RepoWizSample("name1",["ddd":"kkkk"])
        sample.characteristics = ["genotype":"mutant"]

        RepoWizSample sample2 = new RepoWizSample("name2",["ddd":"kkkk"])
        sample2.characteristics = ["genotype":"wild type"]
        //["raw file":["file1.fasta","file2.fasta","file3.fasta"]]
        RepoWizProject project = new RepoWizProject("id",["property":"prop"])

        SubmissionModel model = new SubmissionModel(project,[sample,sample2])

        when:
        def res = model.getAllCharacteristics()

        then:
        res.sort() == ["name1":["genotype":"mutant"],"name2":["genotype":"wild type"]].sort()
    }*/
}
