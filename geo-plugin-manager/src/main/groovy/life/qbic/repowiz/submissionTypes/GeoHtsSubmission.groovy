package life.qbic.repowiz.submissionTypes

import life.qbic.repowiz.download.GeoSubmissionDownloader
import life.qbic.repowiz.mapping.GeoTemplateParser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class GeoHtsSubmission extends GeoSubmission{

    String sheetName = "METADATA TEMPLATE"
    String splitElem = '_'
    ArrayList<Integer> rowsToDelete = [20,21,22]
    //fields with restricted values
    //List seq_type = ["single","paired-end"]

    private GeoTemplateParser parser
    private static final Logger LOG = LogManager.getLogger(GeoHtsSubmission.class)

    GeoHtsSubmission(){
        super.templatePath = "templates/seq_template_v2.1.xlsx"
        parser = new GeoTemplateParser()
        parseTemplate()
    }

    @Override
    void parseTemplate() {
        parser.createWorkbook(super.templatePath)
        parser.parseTemplateSheet(sheetName)
    }

    @Override
    List<String> determineMissingFields(Map filledFields) {
        parser.requiredFields.each {requiredField ->
            if(!filledFields.keySet().contains(requiredField)){
                if(!(super.missingFields.contains(requiredField))) super.missingFields << requiredField
            }
        }

        super.missingFields.addAll(containsOtherRequiredFields(filledFields))

        return super.missingFields
    }

    @Override
    def markMissingFieldsInTemplate(){
        //todo
    }

    @Override
    void writeToWorkbook(HashMap<String, String> project, List<HashMap<String, String>> samples) {
        //first remove rows with examplary entries:
        Collections.sort(rowsToDelete, Collections.reverseOrder()) //sort descending --> first delete lowest row!

        rowsToDelete.each{rowNum ->
            parser.removeRow(sheetName,rowNum)
        }

        //column wise entries
        //series
        parser.writeColumnWise(project)
        //protocols
        writeSectionCol(samples,"protocols")
        //data processing pipeline
        writeSectionCol(samples,"data processing pipeline")

        //for row wise writing:
        //start from bottom to top of sheet

        //paired-end experiment
        writeSectionRow(samples,"paired-end experiments")

        //raw files
        writeSectionRow(samples,"raw files")

        //write samples info
        writeSectionRow(samples,"samples")
        //special case
        //--> multiple insert col bzw move section header cell values to right
        //todo raw file (outermost column.. no changes
        //todo characteristics

    }

    def writeSectionRow(List<HashMap<String, String>> samples, String keyword){
        List filtered = filterForKeyWord(samples, keyword)
        int row = getSectionPosition(keyword)

        if(!filtered.empty && row >= 0) parser.writeRowWise(filtered,sheetName,row)
    }

    def writeSectionCol(List<HashMap<String, String>> samples, String keyword){
        List filtered = filterForKeyWord(samples, keyword)

        if(! filtered.empty) parser.writeColumnWise(filtered[0])
    }

    @Override
    void downloadFile(String fileName) {
        parser.downloadWorkbook(fileName)
    }

    //check if other required fields, that are not marked with a comment are contained within the given fields
    static List containsOtherRequiredFields(Map fields){
        List missing = []
        //some other fields are also required but not marked with a comment
        List required = ["raw files_file name","processed data files_file name"]
        List required_pairedEnd = ["paired-end experiments_file name 1","paired-end experiments_file name 2"]

        required.each {field ->
            if(!fields.keySet().contains(field)) missing << field
        }

        if(fields.containsKey("raw files_single or paired-end") && fields.get("raw files_single or paired-end") == "paired-end"){
            if(!fields.keySet().contains(required_pairedEnd[0])) missing << required_pairedEnd[0]
            if(!fields.keySet().contains(required_pairedEnd[1])) missing << required_pairedEnd[1]
        }

        return missing
    }


    static int getSectionPosition(String section){
        switch (section){
            case "samples":
                return 20
            case "protocols":
                return 26
            case "data processing pipeline":
                return 33
            case "processed data files":
                return 44
            case "raw files":
                return 50
            case "paired-end experiments":
                return 56
            default:
                return -1
        }
    }


    List<HashMap<String,String>> filterForKeyWord(List<HashMap<String, String>> samples, String keyword){
        List filteredList = []

        samples.each {sample ->
            HashMap sampleProps = new HashMap()
            //filter properties of each sample that contain special keyword
            sample.each {cellName, cellValue ->
                //section samples
                if(cellName != null && cellName.split(splitElem)[0] == keyword) sampleProps.put(cellName,cellValue)
            }
            if(!sampleProps.isEmpty()) filteredList << sampleProps
        }
        return filteredList
    }
}
