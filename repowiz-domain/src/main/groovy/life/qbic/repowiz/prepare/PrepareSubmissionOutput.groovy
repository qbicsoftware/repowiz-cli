package life.qbic.repowiz.prepare

interface PrepareSubmissionOutput {

    def transferProjectFiles(List<String> files)
    def transferProjectMetadata(List<File> filledTemplates) //File??

}