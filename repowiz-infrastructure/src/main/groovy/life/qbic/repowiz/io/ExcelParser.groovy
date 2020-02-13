package life.qbic.repowiz.io

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Color
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFFont
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook


class ExcelParser implements TemplateParser{

    String file
    XSSFWorkbook wb
    final short header = 10 //red
    final short fieldName = 12 //blue
    final short example = 8 //black
    final short ifApplicable = 0 //blue
    final String comment = '#'

    //color red: 10 is header
    //color blue: 12 are metadata fields
    //color black: 8 are exemplary fill ins
    //color ??: 0 are variable inputs, they can vary between different projects (e.g multiple lines for the same data)

    ExcelParser(String filePath){
        file = filePath

    }

    @Override
    def parseAsStream() {
        InputStream stream = ExcelParser.class.getClassLoader().getResourceAsStream(file)
        wb = new XSSFWorkbook(stream)
    }

    def parseAsFile(){
        String f = ExcelParser.class.getClassLoader().getResource(file).path
        wb = new XSSFWorkbook(new File(f))
    }

    //specific for hts uploads in geo
    def getMetaDataFields(){
        //get all Metadatafields in first column
        getFieldsFromColumn(0,0)
        //get all metadatafields that are saved row-wise
        //cannot read yellow color from rows -->
        getRowMetaData([20,47,53,59],0)
    }


    //todo need map: assign metadata values to their header --> structure metadata fields to know what to fill in
    //save cells? --> know where to enter data
    //todo ACHTUNG! rows verschieben sich wenn mehr als 3 samples eingeführt werden
    //Idee lies blöcke bis null oder leere zeile (ignorier kommentare)
    def getFieldsFromColumn(int col, int sheetNum){ //save row values --> default write to col 1

        List<String> metadataFields = []

        XSSFSheet sheet = wb.getSheetAt(sheetNum)
        println sheet.sheetName

        sheet.each {row ->
            XSSFCell cell = (XSSFCell) row.getCell(col)

            if (cell != null && !containsComment(cell)){

                //get the style
                XSSFCellStyle cellStyle = cell.cellStyle
                XSSFFont res = cellStyle.font

                if (res.XSSFColor != null){
                    byte[] color =  res.XSSFColor.getRGB()
                    println color
                }

                /*  [-1, 0, 0] rot
                    SERIES
                    [0, 0, -1] blau
                    title */

                println cell.stringCellValue



                if (res.color == fieldName) metadataFields << cell.stringCellValue
                //if (res.color == header) println cell.stringCellValue

            }
        }
        return metadataFields
    }

    List<String> getRowMetaData(List<Integer> rows, int sheetNum){//save col and row value write to row,col
        List<String> metadataFields = []

        Sheet sheet = wb.getSheetAt(sheetNum)

        rows.each { row ->
            row = row-1 //row starts at 0

            //
            sheet.getRow(row).each {cell ->
                def val = cell.stringCellValue
                if(val != null) metadataFields << val
            }
        }

        return metadataFields
    }

    boolean containsComment(Cell cell){
        return cell.stringCellValue.startsWith(comment)
    }


}
