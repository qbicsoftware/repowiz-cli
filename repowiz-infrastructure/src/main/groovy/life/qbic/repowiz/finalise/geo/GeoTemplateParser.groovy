package life.qbic.repowiz.finalise.geo


import life.qbic.repowiz.finalise.parsing.RepositoryInput
import life.qbic.repowiz.finalise.parsing.RepositoryOutput
import life.qbic.repowiz.io.XlsxParser
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import org.apache.poi.xssf.usermodel.XSSFCell

class GeoTemplateParser extends XlsxParser implements RepositoryInput {

    final byte[] rgbLevelColor = [-1, 0, 0]
    final byte[] rgbFieldColor = [0, 0, -1]

    RepositoryOutput output

    GeoTemplateParser(){ //e.g hts ["METADATA TEMPLATE"], affymetrix_ge ["METADATA","MATRIX"] oder so
        super(["METADATA TEMPLATE"])
        super.commentMarker = '#'
        super.mapper = new GeoMapper("hts")
        this.output = output
    }

    def addRepositoryOutput(RepositoryOutput out){
        output = out
    }

    @Override
    def createSubmission(RepoWizProject project, List<RepoWizSample> samples) {
        return null
    }

    @Override
    def parseAsStream(String template){
        super.parseAsStream(template)
    }

    @Override
    def validateProjectInformation(RepoWizProject project, List<RepoWizSample> samples) {
        //if valid --> no missing values
        //else define missing information for repowiz objects and pass them to output
        //output.handelMissingInformation()
        return null
    }

    @Override
    String getRepositoryName() {
        return "geo"
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
