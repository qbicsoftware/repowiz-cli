package life.qbic.repowiz.prepare.parsing

interface GeoParserInput {

    def parseAsStream(String template) //return sheets?
    //HashMap<String,List<String>> parseHtsSheet(String sheet) //put sheet here? actually this is domain knowledge which sheet shall be used..

}