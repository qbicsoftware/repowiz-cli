package life.qbic.repowiz.select


import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import spock.lang.Specification

class SelectRepositorySpecification extends Specification{

    def repositoryDescription = Mock(RepositoryDescription)
    def output = Mock(SelectRepositoryOutput)

    def selectRepository = new SelectRepository(output,repositoryDescription)

    def "defining non suggested repository returns null"(){
        given:
        Repository repo = new Repository(repositoryName:"clinvar",
                dataType: "",
                uploadTypes: [],
                uploadFormat: "",
                uploadRequirements:[],
                characteristics: ["size":"100"],
                subsequentSteps: ["",""])
        selectRepository.setSuggestedRepos([repo])

        when:
        def res = selectRepository.getValidRepository("clinvaar")

        then:
        res == null
    }

    def "defining valid repository "(){
        given:
        Repository repo = new Repository(repositoryName:"clinvar",
                dataType: "",
                uploadTypes: [],
                uploadFormat: "",
                uploadRequirements:[],
                characteristics: ["size":"100"],
                subsequentSteps: ["",""])
        selectRepository.setSuggestedRepos([repo])

        when:
        def res = selectRepository.getValidRepository("clinvar")

        then:
        res == repo
    }

    def "retrieve correct repository"(){
        given:
        Repository repo = new Repository(repositoryName:"clinvar",
                dataType: "",
                uploadTypes: [],
                uploadFormat: "",
                uploadRequirements:[],
                characteristics: ["size":"100"],
                subsequentSteps: ["",""])
        selectRepository.setSuggestedRepos([repo])

        when:
        def res = selectRepository.getValidRepository("clinvar")

        then:
        res == repo
    }
}
