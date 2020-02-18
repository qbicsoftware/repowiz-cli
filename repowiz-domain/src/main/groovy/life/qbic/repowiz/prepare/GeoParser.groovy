package life.qbic.repowiz.prepare

interface GeoParser {

    def parseTemplate(String template)
    HashMap<String,List<String>> parseSheetByColor(String sheet)

}