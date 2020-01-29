package life.qbic.repowiz


interface RepositoryDescription {

    List<Repository> findRepository(List<String> repositoryNames)
    Repository findRepositoryByName(String repository)

}