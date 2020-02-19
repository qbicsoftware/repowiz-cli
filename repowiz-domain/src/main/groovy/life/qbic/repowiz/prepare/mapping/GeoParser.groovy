package life.qbic.repowiz.prepare.mapping

interface GeoParser {

    def parseTemplate(String template)
    HashMap<String,List<String>> parseSheetByColor(String sheet)

}