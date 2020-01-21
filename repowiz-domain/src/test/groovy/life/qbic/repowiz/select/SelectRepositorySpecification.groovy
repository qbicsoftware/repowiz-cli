package life.qbic.repowiz.select

import life.qbic.repowiz.ProjectDetails
import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import spock.lang.Specification

class SelectRepositorySpecification extends Specification{

    def projectSpecification = Mock(ProjectDetails)
    def repositoryDescription = Mock(RepositoryDescription)
    def output = Mock(SelectRepositoryOutput)

    def selectRepository = new SelectRepository(projectSpecification,repositoryDescription,output)


    def "Specified Repository must be one of the suggested repositories"(){
        given:
        Repository ena = new Repository("ena","sequence data",["sequencing","genomics"],true,[])
        List<Repository> repos = [ena]
        String repoName = "ena"

        when:
        def result = selectRepository.isSuggestedRepository(repoName,repos)

        then:
        result
    }

    def "Any Repository can be chosen if there are no repository suggested"(){
        given:
        List<Repository> repos = []
        String repoName = "ena"

        when:
        def result = selectRepository.isSuggestedRepository(repoName,repos)

        then:
        result
    }

}
