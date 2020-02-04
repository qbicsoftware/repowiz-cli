package life.qbic.repowiz.find


import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryDescription
import life.qbic.repowiz.utils.IO

import java.nio.file.DirectoryStream
import java.nio.file.Path

class RepositoryDatabaseConnector implements RepositoryDescription{

    String path = "repositories/"

    @Override
    List<Repository> findRepository(List<String> repositoryNames) {

        List allRepositories = IO.getFilesFromDirectory(path)

        List<Repository> repositories = []

        //create repository object from repository file
        allRepositories.each { fileName ->
            repositories << getRepository(path+fileName)
        }

        return repositories
    }

    Repository getRepository(String fileURL){
        print fileURL

        def filePath = RepositoryDatabaseConnector.class.getClassLoader().getResourceAsStream(fileURL)
        def repoInfo = IO.parseJsonStream(filePath)


        assert repoInfo instanceof Map
        createRepoFromJSON(repoInfo)
    }

    Repository createRepoFromJSON(Map repoMap){
        String name = repoMap.get("repositoryName")
        String repositoryType = repoMap.get("repositoryName")
        List<String> experimentTypes = repoMap.get("experimentType")
        String uploadType = repoMap.get("uploadType")
        List<String> uploadRequirements = repoMap.get("uploadRequirements")
        String size = repoMap.get("size")

        Repository repo = new Repository(name,repositoryType,experimentTypes,uploadType,uploadRequirements)
        repo.addCharacteristic("size",size)

        return repo
    }
}
