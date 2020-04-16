package life.qbic.repowiz


interface RepositoryDescription {

    Repository findRepository(String fileURL)
    List<Repository> findRepositories(List<String> repoNames)

}