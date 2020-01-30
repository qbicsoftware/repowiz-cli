package life.qbic.repowiz


interface RepositoryDescription {

    List<Repository> findRepository(List<String> repositoryNames)

}