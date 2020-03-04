package life.qbic.repowiz.prepare.projectSearch.geo

import life.qbic.repowiz.io.XlsxParser
import org.apache.poi.xssf.usermodel.XSSFCell

class GeoParser extends XlsxParser{

    final byte[] rgbLevelColor = [-1, 0, 0]
    final byte[] rgbFieldColor = [0, 0, -1]

    GeoParser(List<String> templateSheets){ //e.g hts ["METADATA TEMPLATE"], affymetrix_ge ["METADATA","MATRIX"] oder so
        super(templateSheets)
        super.commentMarker = '#'
        super.mapper = new GeoMapper("hts")
    }

    //a required field does not contain the keyword "[optional]" within the cells comment
    def isRequired(XSSFCell cell){
        boolean required = false

        if(cell.cellComment != null){
            String cellComment = cell.cellComment.string.string.toLowerCase()
            required = !cellComment.contains("[optional]")
        }
        return required
    }

    //a section is defined by the rgb color red
    def isSection(XSSFCell cell){
        byte[] color = getRGBColor(cell)
        int col = cell.getColumnIndex()

        return color != null && color == rgbLevelColor && col == 0
    }

    //a field is defined by the rbg color blue
    def isField(XSSFCell cell){
        byte[] color = getRGBColor(cell)
        int col = cell.getColumnIndex()

        return color != null && color == rgbFieldColor || col > 0
    }

    //a cell is valid if it is not empty, not null and it is no cell only containing comments
    def isValidCell(XSSFCell cell){
        return cell != null && cell.stringCellValue != "" && !commentCell(cell) && !cell.stringCellValue.toLowerCase().contains("[optional]")
    }

}
