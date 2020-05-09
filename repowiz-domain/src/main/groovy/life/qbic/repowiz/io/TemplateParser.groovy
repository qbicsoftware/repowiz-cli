package life.qbic.repowiz.io

interface TemplateParser {

    /**
     * Function to create a workbook from a given filename
     * @param fileName
     * @return
     */
    def createWorkbook(String fileName)

}