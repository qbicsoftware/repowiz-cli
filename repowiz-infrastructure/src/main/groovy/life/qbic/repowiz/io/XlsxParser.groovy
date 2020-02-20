package life.qbic.repowiz.io

import life.qbic.repowiz.prepare.mapping.GeoParser

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFWorkbook


class XlsxParser implements TemplateParser, GeoParser{

    XSSFWorkbook wb

    final String comment = '#'
    final byte[] rgbLevelColor = [-1, 0, 0]
    final byte[] rgbFieldColor = [0, 0, -1]


    @Override
    def parseAsStream(String file) {
        InputStream stream = XlsxParser.class.getClassLoader().getResourceAsStream(file)

        wb = (XSSFWorkbook) new XSSFWorkbook(stream)
    }

    def parseAsFile(String file) {
        String f = XlsxParser.class.getClassLoader().getResource(file).path
        wb = new XSSFWorkbook(new File(f))
    }


    //for geo it is required to parse the sheet by color
    @Override
    def parseTemplate(String template) {
        parseAsStream(template)
    }

    //todo for geo bold terms are required! the thin ones are optional!! --> encode that in repowiz structure??!?

    @Override
    HashMap<String,List<String>> parseSheetByColor(String sheetName) {
        HashMap res = null

        wb.sheetIterator().each { sheet ->
            if (sheet.sheetName.trim() == sheetName) { //need to strip succeeding whitespaces
                res = getExperimentLevels(sheet)
            }
        }
        return res
    }

    def getExperimentLevels(Sheet sheet) {

        HashMap<String, List<String>> classWithFields = new HashMap<>()
        int column = 0

        sheet.each { row ->

            XSSFCell cell = (XSSFCell) row.getCell(column)

            if (cell != null && cell.stringCellValue != "" && !containsComment(cell)) {

                byte[] color = getRGBColor(cell)

                if (isLevel(color,column)) {
                    String level = cell.stringCellValue.trim()
                    List<String> fields = getExperimentFields(row.rowNum, sheet)
                    //level is all fields assigned to a section in the metadata sheet
                    classWithFields.put(level, fields)
                }
            }
        }
        return classWithFields
    }

    List<String> getExperimentFields(int levelRow, Sheet sheet) {
        boolean nextLevel = false
        List<String> metaFields = []

        //iterate up to next level
        while (!nextLevel) {
            levelRow += 1
            Row row = sheet.getRow(levelRow)

            if (row == null) {
                break
            }

            for (int col = 0; col < row.size(); col++) {
                XSSFCell cell = (XSSFCell) row.getCell(col)

                if (isValidCell(cell)) {
                    byte[] color = getRGBColor(cell)

                    if (isLevel(color,col)) {
                        nextLevel = true
                    }
                    if (isField(color,col)) {
                        metaFields.add(cell.stringCellValue.trim())
                    }
                }
            }
        }
        return metaFields
    }

    byte[] getRGBColor(XSSFCell cell) {
        XSSFCellStyle cellStyle = cell.cellStyle
        XSSFFont res = cellStyle.font

        if (res.XSSFColor != null) {
            return res.XSSFColor.getRGB()
        }
        return null
    }

    def isLevel(byte[] color, int col){
        return color != null && color == rgbLevelColor && col == 0
    }

    def isField(byte[] color, int col){
        return color != null && color == rgbFieldColor || col > 0
    }

    def isValidCell(XSSFCell cell){
        return cell != null && cell.stringCellValue != "" && !containsComment(cell)
    }

    boolean containsComment(Cell cell) {
        return cell.stringCellValue.startsWith(comment)
    }
}
