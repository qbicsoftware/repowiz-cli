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
        Repository repo = new Repository("clinvar","",[""],"",[""])
        selectRepository.setSuggestedRepos([repo])

        when:
        def res = selectRepository.isValidRepository("clinvaar")

        then:
        !res
    }

    def "defining valid repository "(){
        given:
        Repository repo = new Repository("clinvar","",[""],"",[""])
        selectRepository.setSuggestedRepos([repo])

        when:
        def res = selectRepository.isValidRepository("clinvar")

        then:
        res
    }

    def "retrieve correct repository"(){
        given:
        Repository repo = new Repository("clinvar","",[""],"",[""])
        selectRepository.setSuggestedRepos([repo])

        when:
        def res = selectRepository.getValidRepository("clinvar")

        then:
        res == repo
    }
}
