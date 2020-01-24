package life.qbic.repowiz.select

import life.qbic.repowiz.ProjectDetails
import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import spock.lang.Specification

class SelectRepositorySpecification extends Specification{

    def repositoryDescription = Mock(RepositoryDescription)
    def output = Mock(SelectRepositoryOutput)

    def selectRepository = new SelectRepository(output,repositoryDescription)


}
