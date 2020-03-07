package life.qbic.repowiz.io

import life.qbic.repowiz.prepare.mapping.RepositoryMapper
import life.qbic.repowiz.prepare.parsing.GeoParserInput
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFWorkbook


abstract class XlsxParser implements TemplateParser, GeoParserInput{

    private static final Logger LOG = LogManager.getLogger(XlsxParser.class)

    XSSFWorkbook wb
    List sheets
    List requiredFields = []
    HashMap<String,XSSFCell> templateFields = new HashMap<>()
    String section

    String commentMarker
    RepositoryMapper mapper

    XlsxParser(List<String> templateSheets){ //e.g hts ["METADATA TEMPLATE"], affymetrix_ge ["METADATA","MATRIX"] oder so
        sheets = templateSheets
    }

    @Override
    def parseAsStream(String file) {
        InputStream stream = XlsxParser.class.getClassLoader().getResourceAsStream(file)

        wb = (XSSFWorkbook) new XSSFWorkbook(stream)
    }

    abstract def isSection(XSSFCell cell)
    abstract def isField(XSSFCell cell)
    abstract def isRequired(XSSFCell cell)
    abstract def isValidCell(XSSFCell cell)
    //method to write information back/to know where to write information
    //abstract HashMap<String,List<String>> writeToSheet(String sheetName)

    def parseTemplateSheet(String sheetName) {
        //HashMap<String, List<String>> classWithFields = new HashMap<>()

        wb.sheetIterator().each { sheet ->
            //if (sheets.contains(sheet.sheetName.trim())) { //need to strip succeeding whitespaces todo write as regex
            if (sheetName == sheet.sheetName.trim()) {
                sheet.each { row ->

                    for (int col = 0; col < row.size(); col++) {

                        XSSFCell cell = (XSSFCell) row.getCell(col)

                        if (isValidCell(cell) && isSection(cell)) {
                            section = cell.stringCellValue.trim().toLowerCase() //need section because of duplicate field names
                            getSectionFields(row.rowNum, sheet)

                            //section is all fields assigned to a section in the metadata sheet
                            //classWithFields.put(section, fields)
                        }
                    }
                }
            }
        }

    }

    def getSectionFields(int rowNum, Sheet sheet) {
        boolean nextSection = false

        //iterate up to next section
        while (!nextSection) {
            rowNum += 1
            Row row = sheet.getRow(rowNum)

            if (row == null) {
                break
            }

            for (int col = 0; col < row.size(); col++) {
                XSSFCell cell = (XSSFCell) row.getCell(col)

                if (isValidCell(cell)) {
                    if (isSection(cell)) {
                        nextSection = true
                    }
                    if (isField(cell)){
                        String rawValue = cell.stringCellValue.trim()
                        //LOG.debug("Masking Geo terms ...")
                        String maskedValue = maskDuplicates(rawValue,section)
                        //LOG.debug("Mapping Geo terms to RepoWiz terms ...")
                        String cellValue = mapper.mapPropertiesToRepoWiz(maskedValue)

                        templateFields.put(cellValue,cell)

                        if(isRequired(cell)) requiredFields.add(cellValue)

                    }
                }
            }
        }
    }

    static byte[] getRGBColor(XSSFCell cell) {
        XSSFCellStyle cellStyle = cell.cellStyle
        XSSFFont res = cellStyle.font

        if (res.XSSFColor != null) {
            return res.XSSFColor.getRGB()
        }
        return null
    }

    boolean commentCell(Cell cell) {
        return cell.stringCellValue.startsWith(commentMarker)
    }

    static def maskDuplicates(String prop, String section){
        /*List masked = []

        prop.each {
            masked << section + "_" + it
        }

        return masked*/
        return section + "_" + prop
    }


}
