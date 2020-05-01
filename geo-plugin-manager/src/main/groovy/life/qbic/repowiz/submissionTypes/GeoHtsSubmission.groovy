package life.qbic.repowiz.submissionTypes

import life.qbic.repowiz.model.GeoSample
import life.qbic.repowiz.mapping.GeoTemplateParser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.poi.xssf.usermodel.XSSFCell
import org.jcodings.util.Hash

class GeoHtsSubmission extends GeoSubmission{

    String sheetName = "METADATA TEMPLATE"
    String splitElem = '_'
    ArrayList<Integer> rowsToDelete = [20,21,22]
    //fields with restricted values
    //List seq_type = ["single","paired-end"]
    List fileTypes = ["fastq", "Illumina_native_qseq", "Illumina_native" ,"SOLiD_native_csfasta",
                      "SOLiD_native_qual", "sff", "454_native_seq", "454_native_qual", "Helicos_native", "srf",
                      "PacBio_HDF5"]

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

        deleteSampleRows()
    }

    def deleteSampleRows(){
        //first remove rows with examplary entries:
        Collections.sort(rowsToDelete, Collections.reverseOrder()) //sort descending --> first delete lowest row!

        rowsToDelete.each{rowNum ->
            parser.removeRow(sheetName,rowNum)
        }
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
    void writeProjectToWorkbook(HashMap<String, String> project){
        //column wise entry
        //series
        parser.writeColumnWise(project)
    }

    @Override
    void writeSampleToWorkbook(List<GeoSample> samples) {
        //column wise entry
        //protocols
        writeSectionCol(samples,"protocols")
        //data processing pipeline
        writeSectionCol(samples,"data processing pipeline")

        //for row wise writing:
        //start from bottom to top of sheet!
        //paired-end experiment
        writeSectionRow(samples,"paired-end experiments")
        //todo paired-end data
        //"raw files_single or paired-end" spalte
        //paired end illumina file handling!
        //https://support.illumina.com/help/BaseSpace_OLH_009008/Content/Source/Informatics/BS/NamingConvention_FASTQ-files-swBS.htm

        //raw files
        writeRawFileSection(samples)

        //prepare samples section header
        List characteristicTypes = getAllCharacteristicsTags(samples)
        int maxFiles = maxNumFiles(samples)

        writeSamplesSectionHeader(characteristicTypes,maxFiles)

        //write samples info
        writeSectionRow(samples,"samples")
        //special case
        //--> multiple insert col bzw move section header cell values to right
        //todo raw file (outermost column.. no changes
        //todo characteristics: here only max 3 characteristics are expected adjust!!
        writeCharacteristicsSection(samples)
        //test
       // parser.addColumnToEnd(sheetName,12)
    }

    @Override
    void downloadFile(String fileName) {
        parser.downloadWorkbook(fileName)
    }

    def writeSectionRow(List<GeoSample> samples, String keyword){
        List filtered = filterForKeyWord(samples, keyword)
        int row = getSectionPosition(keyword)

        if(!filtered.empty && row >= 0) parser.writeRowWise(filtered,sheetName,row)
    }

    def writeSectionCol(List<GeoSample> samples, String keyword){
        List filtered = filterForKeyWord(samples, keyword)

        if(!filtered.empty) parser.writeColumnWise(filtered[0])
    }

    def writeSamplesSectionHeader(List<String> characteristicTypes, int maxNumRawFiles){
        //rawfiles todo
        //XSSFCell cell = parser.templateFields.get("samples_raw file")

        //write characteristics
        //todo extend to write more than 3 characteristics
        List<Integer> colNums = [4,5,6]
        int rowNum = getSectionPosition("samples")-1
        int counter = 0

        colNums.each {colNum ->
            if (characteristicTypes[counter] != null) parser.setCellValue(sheetName, rowNum, colNum, characteristicTypes[counter])
            counter ++
        }

    }

    def writeCharacteristicsSection(List<GeoSample> samples) {
        //need row num for the characteristics
        int baseRowNum = getSectionPosition("samples") - 1
        //need colnum for each characteristic
        samples.each {sample ->
            //+ sample number
            String sampleNum = sample.sampleName.split(" ")[1]
            int offset = sampleNum as Integer
            int rowNum = baseRowNum + offset

            sample.characteristics.each {characteristic ->
                XSSFCell cell = parser.templateFields.get(characteristic.key.toString())

                parser.setCellValue(sheetName,rowNum,cell.columnIndex,characteristic.value.toString())
            }
        }

    }

    def writeRawFileSection(List<GeoSample> samples){
        List filtered = []
        int row = getSectionPosition("raw files")

        //todo add information not given by samples
        samples.each {sample ->
            //hashmap --> filename needs to be unique!
            HashMap fileProperties = new HashMap()

            //instrument model
            sample.properties.each { prop ->
                if (prop.key.toString().contains("raw files")) fileProperties.put(prop.key, prop.value)
            }

            sample.rawFiles.each {file ->
                //file name
                fileProperties.put("raw files_file name",file)
                //file type
                String[] fileSplit = file.split("\\.")

                if(fileSplit.size() == 2 || fileSplit.size() == 3){
                    String fileType = fileSplit[1]

                    if(fileTypes.contains(fileType)) fileProperties.put("raw files_file type",fileType)
                    else{
                        LOG.info("The file type for file $file is not accepted please check manually")
                    }
                }
                else{
                    LOG.info("The file type for file $file is not valid please check manually")
                }

                if(fileProperties.size() > 0 && row >= 0) parser.writeRowWise([fileProperties],sheetName,row)

            }
            //file checksum

            //read length

            //single or paired-end

            //filtered << fileProperties
        }
        //if(!filtered.empty && row >= 0) parser.writeRowWise(filtered,sheetName,row)
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

    List<HashMap<String,String>> filterForKeyWord(List<GeoSample> samples, String keyword){
        List<HashMap> filteredList = []

        samples.sort(){a,b -> a.sampleName <=> b.sampleName}

        samples.each {sample ->
            HashMap sampleProps = new HashMap()
            //filter properties of each sample that contain special keyword
            sample.properties.each {cellName, cellValue ->
                //section samples
                if(cellName != null && cellName.split(splitElem)[0] == keyword) sampleProps.put(cellName,cellValue)
            }
            if(!sampleProps.isEmpty()) filteredList << sampleProps
        }

        return filteredList
    }

    static List<String> getAllCharacteristicsTags(List<GeoSample> samples){
        List<String> allCharact = []
        samples.each {sample ->
            sample.characteristics.keySet().each {characteristic ->
                if(! allCharact.contains(characteristic)) allCharact << characteristic.toString()
            }
        }
        return allCharact
    }

    static int maxNumFiles(List<GeoSample> samples){
        int max = -1

        samples.each {sample ->
            int numFiles = 0
            if(sample.rawFiles != null) numFiles = sample.rawFiles.size()

            if(numFiles > max) max = numFiles
        }

        return max
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
}
