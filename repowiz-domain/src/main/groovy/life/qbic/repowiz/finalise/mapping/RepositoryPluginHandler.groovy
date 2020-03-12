package life.qbic.repowiz.finalise.mapping


import life.qbic.repowiz.finalise.parsing.RepositoryInput
import life.qbic.repowiz.finalise.parsing.RepositoryOutput
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.repowiz.prepare.model.RepoWizSample
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class RepositoryPluginHandler implements SubmissionToPlugin, RepositoryOutput{

    RepositoryInput goalRepository
    List<RepositoryInput> repositories

    private static final Logger LOG = LogManager.getLogger(RepositoryPluginHandler.class)

    RepositoryPluginHandler(List<RepositoryInput> repoList){
        this.repositories = repoList
    }

    //todo do i need that?
    @Override
    def addOutput(PluginToSubmission output) {
        return null
    }

    @Override
    def determineRepositoryPlugin(String repositoryName, String uploadType) {
        String templateName = "templates/"
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
                //todo ask for platform accession number, if not provided chose template with platform fillout
                templateName = ""
                //sheets.add("")
                break
        }
    }

    @Override
    def transferDataToRepositoryPlugin(RepoWizProject project, List<RepoWizSample> samples) {
        //todo do something with PARSER --> trigger validation!
        return null
    }

    def setGoalRepository(String repositoryName){
        repositories.each {repo ->
            if(repo.repositoryName == repositoryName){
                goalRepository = repo
            }else{
                //todo throw exception --> cannot upload to a repository with no plugin (should not happen)
                LOG.error "The selected repository has not corresponding plugin to prepare your data"
            }

        }
    }

    //todo handle output of parser --> incorrporate missing values ... and handle validity status with user
    @Override
    def handelMissingInformation(List missingValues) {
        return null
    }
}
