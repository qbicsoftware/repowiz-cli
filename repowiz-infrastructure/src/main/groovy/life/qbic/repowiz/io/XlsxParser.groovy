package life.qbic.repowiz.io

import life.qbic.repowiz.prepare.GeoParser
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook


class XlsxParser implements TemplateParser, GeoParser {

    String file
    XSSFWorkbook wb

    final String comment = '#'


    XlsxParser(String filePath) {
        file = filePath
    }

    XlsxParser() {

    }

    //todo clean up this mess --> keep all these methods or stick with only one type of template parsing (template with constructor/template with method)
    @Override
    def parseAsStream() {
        InputStream stream = XlsxParser.class.getClassLoader().getResourceAsStream(file)
        wb = new XSSFWorkbook(stream)
    }

    def parseAsFile() {
        String f = XlsxParser.class.getClassLoader().getResource(file).path
        wb = new XSSFWorkbook(new File(f))
    }


    //for geo it is required to parse the sheet by color
    @Override
    def parseTemplate(String template) {
        InputStream stream = XlsxParser.class.getClassLoader().getResourceAsStream(template)
        wb = new XSSFWorkbook(stream)
    }

    @Override
    def parseSheetByColor(String sheetName) {
        byte[] header = [-1, 0, 0]
        byte[] field = [0, 0, -1]
        HashMap test = null

        wb.sheetIterator().each { sheet ->
            if (sheet.sheetName.strip() == sheetName) { //need to strip succeeding whitespaces
                test = getMetaDataFieldsByColor(sheet, header, field)
            }
        }
        return test
    }

    def getMetaDataFieldsByColor(Sheet sheet, byte[] header, byte[] field) {

        HashMap<String, List<String>> classWithFields = new HashMap<>()

        sheet.each { row ->

                XSSFCell cell = (XSSFCell) row.getCell(0)

                if (cell != null && cell.stringCellValue != "" && !containsComment(cell)) {

                    //get the style
                    XSSFCellStyle cellStyle = cell.cellStyle
                    XSSFFont res = cellStyle.font

                    if (res.XSSFColor != null) {
                        byte[] color = res.XSSFColor.getRGB()
                        if (color == header) {
                            String level = cell.stringCellValue
                            List<String> fields = getMetaFieldsForLevel(row.rowNum, sheet, header, field)
                            classWithFields.put(level, fields)
                        }
                    }

            }
        }
        return classWithFields
    }

    List<String> getMetaFieldsForLevel(int headerRow, Sheet sheet, byte[] header, byte[] field) {
        boolean nextHeader = false
        List<String> metaFields = []
        //iterate until a new header appears
        while (!nextHeader) {
            headerRow += 1
            Row row = sheet.getRow(headerRow)

            if (row == null) {
                break
            }

            for (int col = 0; col < row.size(); col++) {
                XSSFCell cell = (XSSFCell) row.getCell(col)

                if (cell != null && cell.stringCellValue != "" && !containsComment(cell)) {
                    println cell.stringCellValue

                    //get the style
                    XSSFCellStyle cellStyle = cell.cellStyle
                    XSSFFont res = cellStyle.font

                    if (res.XSSFColor != null) {
                        byte[] color = res.XSSFColor.getRGB()

                        if (color == header && col == 0) {
                            nextHeader = true
                        }
                        if (color == field || col > 0) {
                            metaFields.add(cell.stringCellValue)
                        }
                    }
                }
            }
        }

        return metaFields
    }


    def getFieldsFromColumn(int col, int sheetNum) { //save row values --> default write to col 1

        List<String> metadataFields = []

        XSSFSheet sheet = wb.getSheetAt(sheetNum)
        //println sheet.sheetName


        sheet.each { row ->
            XSSFCell cell = (XSSFCell) row.getCell(col)


            if (cell != null && !containsComment(cell)) {

                //get the style
                XSSFCellStyle cellStyle = cell.cellStyle
                XSSFFont res = cellStyle.font

                if (res.XSSFColor != null) {
                    byte[] color = res.XSSFColor.getRGB()

                    metadataFields << cell.stringCellValue

                    println color
                }

                println cell.stringCellValue
            }
        }
        return metadataFields
    }

    def getRowMetaData(int row, int sheetNum) {//save col and row value write to row,col
        List<String> metadataFields = []

        XSSFSheet sheet = wb.getSheetAt(sheetNum)

        sheet.getRow(row).each { cell ->
                def val = cell.stringCellValue
                if (val != null) metadataFields << val

        }

        return metadataFields
    }

    boolean containsComment(Cell cell) {
        return cell.stringCellValue.startsWith(comment)
    }
}
