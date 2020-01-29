package life.qbic.repowiz


import life.qbic.repowiz.find.SubmissionTypes

interface RepositoryDescription {

    List<Repository> findRepository(List<String> repositoryNames)
    Repository findRepositoryByName(String repository)

}