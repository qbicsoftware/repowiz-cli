package life.qbic.repowiz.find

import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.utils.IO

class RepositoryDatabaseConnector implements RepositoryDescription{

    File repoDir

    RepositoryDatabaseConnector(String pathToRepositoryDirectory){
        def dirURL = getClass().getResource(pathToRepositoryDirectory)
        repoDir = new File(dirURL.toURI())
    }

    @Override
    List<Repository> findRepository(List<String> repositoryNames) {

        List allRepositories = IO.getFilesFromDirectory(repoDir)

        //determine files for repositories

        //parse files to Repository Objects and return them

        return null
    }

}
