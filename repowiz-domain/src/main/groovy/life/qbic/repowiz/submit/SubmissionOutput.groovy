package life.qbic.repowiz.submit

interface SubmissionOutput {

    def getMetaData(HashMap<String,String> metadata)
    def getFiles(List<String> filePaths)

}