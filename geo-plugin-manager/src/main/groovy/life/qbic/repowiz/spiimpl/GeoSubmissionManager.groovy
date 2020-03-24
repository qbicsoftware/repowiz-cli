package life.qbic.repowiz.spiimpl

import life.qbic.repowiz.finalise.api.SubmissionManager
import life.qbic.repowiz.prepare.model.SubmissionModel
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class GeoSubmissionManager implements SubmissionManager{

    private static final Logger LOG = LogManager.getLogger(GeoSubmissionManager.class)


    @Override
    SubmissionModel validateSubmissionModel(SubmissionModel model) {
        //todo load all required fields for valid submission
        //todo load all accepted fields
        //todo answer with validation status and missing fields
        return model
    }

    def mapMetadataModel(){

    }

    def uploadType(){
        //todo where to get info about uploadtype
        /*String templateName = "templates/"
        //List<String> sheets = []
        setGoalRepository(repositoryName)

        switch (uploadType){
            case "hts":
                //call method for hts template
                templateName += "seq_template_v2.1.xlsx"
                //sheets.add("METADATA TEMPLATE")
                break
            case "affymetrix_GE":
                //whole gene expression
                //todo ask for platform accession number, if not provided chose template with platform fill out
                templateName = ""
                //sheets.add("")
                break
        }*/
    }

}
