package life.qbic.repowiz.prepare


import life.qbic.repowiz.Repository

class PrepareSubmissionImpl implements PrepareSubmissionInput, ProjectSpecification{

    MappedMetadata mappedMetadata
    PrepareSubmissionOutput output

    PrepareSubmissionImpl(MappedMetadata mappedMetadata, PrepareSubmissionOutput output){
        this.mappedMetadata = mappedMetadata
        this.output = output
    }

    @Override
    def prepareSubmissionToRepository(Repository repository) {
        //project data
        //transfer to output
        //output.transferProjectFiles()

        return null
    }

    //get the project code from somewhere.. controller structure!
    @Override
    def getProject(String projectID) {
        //connect to local database to retrieve data
        return null
    }

}