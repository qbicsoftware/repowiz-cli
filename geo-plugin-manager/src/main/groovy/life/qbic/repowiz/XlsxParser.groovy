package life.qbic.repowiz

import life.qbic.repowiz.io.TemplateParser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook


abstract class XlsxParser implements TemplateParser{

    private static final Logger LOG = LogManager.getLogger(XlsxParser.class)

    XSSFWorkbook wb
    List<String> requiredFields = []
    HashMap<String,XSSFCell> templateFields = new HashMap<>()
    String section

    String commentMarker

    @Override
    def createWorkbook(String file) {
        InputStream stream = XlsxParser.class.getClassLoader().getResourceAsStream(file)

        wb = (XSSFWorkbook) new XSSFWorkbook(stream)
    }

    def writeColumnWise(HashMap<String,String> values){

        values.entrySet().each{colName ->

            println templateFields.get(colName.key)

            XSSFCell cell = templateFields.get(colName.key)
            XSSFCell cellWithValue = cell.row.getCell(cell.columnIndex + 1)
            cellWithValue.setCellValue(colName.value)
        }
    }

    //call it per section! only bulk write
    def writeRowWise(List<HashMap<String,String>> rowValues, String sheetName, int rowAt){
        XSSFSheet sheet = getSheet(sheetName)

        //each list entry contains elements per row
        rowValues.each {rowEntry ->
            //start one row below the header row
            //shift all rows from rowAt - end of sheet, shift n number of rows
            sheet.shiftRows(rowAt, sheet.getLastRowNum(), 1, true, false)

            ///new empty row
            sheet.createRow(rowAt)
            XSSFRow newRow = sheet.getRow(rowAt)

            //write content into this row
            rowEntry.each {cellName, cellValue ->
                //find column for new value
                if(templateFields.get(cellName) != null){
                    XSSFCell cell = templateFields.get(cellName)
                    int colNum = cell.columnIndex

                    //write new value to right column
                    XSSFCell cellForValue = newRow.createCell(colNum)
                    cellForValue.setCellValue(cellValue.toString())
                }
            }

            rowAt++
        }
    }

    def downloadWorkbook(String fileName){
        File file = new File(fileName+".xlsx")
        //ignore old files with same name
        file.createNewFile()
        FileOutputStream out = new FileOutputStream(file)

        wb.write(out)
    }

    void removeRow(String sheetName, int rowIndex){
        Sheet sheet = getSheet(sheetName)
        removeRow(sheet,rowIndex)
    }

    static void removeRow(Sheet sheet, int rowIndex) {
        int lastRowNum = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
        }
        if (rowIndex == lastRowNum) {
            Row removingRow = sheet.getRow(rowIndex);
            if (removingRow != null) {
                sheet.removeRow(removingRow);
            }
        }
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

    XSSFSheet getSheet(String sheetName){
        XSSFSheet foundSheet = null

        wb.sheetIterator().each { sheet ->
            //if (sheets.contains(sheet.sheetName.trim())) { //need to strip succeeding whitespaces todo write as regex
            if (sheetName == sheet.sheetName.trim()) {
                foundSheet = (XSSFSheet) sheet
            }
        }

        return foundSheet
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
                        //String cellValue = mapper.mapPropertiesToRepoWiz(maskedValue)

                        templateFields.put(maskedValue,cell)

                        if(isRequired(cell)) requiredFields.add(maskedValue)

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
        return section + "_" + prop
    }


}
