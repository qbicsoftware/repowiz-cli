package life.qbic.repowiz.io

interface TemplateParser {

    //do i need the first method? --> probably stream is enough
    //def parseAsFile()
    def parseAsStream(String fileName)

}