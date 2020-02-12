package life.qbic.repowiz.io

interface TemplateParser {

    //do i need the first method? --> probably stream is enough
    //def parseFile(File file)
    def parseStream(InputStream stream)

}