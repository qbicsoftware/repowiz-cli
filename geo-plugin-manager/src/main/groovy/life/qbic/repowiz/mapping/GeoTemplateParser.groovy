package life.qbic.repowiz.mapping

import life.qbic.repowiz.XlsxParser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.poi.xssf.usermodel.XSSFCell

import java.sql.Timestamp
import java.text.SimpleDateFormat

class GeoTemplateParser extends XlsxParser {

    final byte[] rgbLevelColor = [-1, 0, 0]
    final byte[] rgbFieldColor = [0, 0, -1]

    private static final Logger LOG = LogManager.getLogger(GeoTemplateParser.class)


    GeoTemplateParser(){
        super.commentMarker = '#'
    }

    @Override
    def createWorkbook(String template){
        super.createWorkbook(template)
    }

    def writeToWorkbook(HashMap<String, String> project, List<HashMap<String, String>> samples, String sheet){
        /*values.each {k,v ->
            super.write(k,v)//method from xlsx parser
        }*/
        super.writeColumnWise(project)
        //todo what about columnwise values in samples --> htssubmission specific!!
        //todo split this function to columnwise
        //rowwise
        //sort by section!!!
        //samples = 20
        List sample_samples = []
        samples.each {sample ->
            HashMap sampleProps = new HashMap()
            //filter properties of each sample that contain special keyword
            sample.each {cellName, cellValue ->
                //section samples
                if(cellName != null && cellName.split("_")[0] == "samples") sampleProps.put(cellName,cellValue)
            }
            sample_samples << sampleProps
        }

        super.writeRowWise(sample_samples,sheet,20)

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
