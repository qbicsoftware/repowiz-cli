package life.qbic.repowiz

interface RepositoryDescription {

    List<Repository> findRepository(HashMap<String,String> submissionSpecification)
    Repository findRepositoryByName(String repository)

}