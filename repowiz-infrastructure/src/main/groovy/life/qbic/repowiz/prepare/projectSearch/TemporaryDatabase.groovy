package life.qbic.repowiz.prepare.projectSearch

class TemporaryDatabase {

    HashMap openBisToRepoWiz
    HashMap repoWizToGeo

    TemporaryDatabase(){
        fillLocalDbMap()
    }

    def fillLocalDbMap(){
        openBisToRepoWiz = new HashMap()
        openBisToRepoWiz.put("Q_PROJECT_DETAILS","design")
        openBisToRepoWiz.put("Q_PRIMARY_TISSUE","source name")
        openBisToRepoWiz.put("Q_NCBI_ORGANISM","organism")
        openBisToRepoWiz.put("Q_SAMPLE_TYPE","molecule")
        openBisToRepoWiz.put("Q_SEQUENCER_DEVICE","instrument model")
        openBisToRepoWiz.put("Q_SEQUENCING_MODE","sequencing mode")
        openBisToRepoWiz.put("Q_SEQUENCING_TYPE","sequencing type")
        openBisToRepoWiz.put("Q_ADDITIONAL_INFO_SAMPLE","library protocol")
        openBisToRepoWiz.put("Q_ADDITIONAL_INFO_EXPERIMENT","ref genome")
        openBisToRepoWiz.put("Q_NGS_RAW_DATA","raw file") //are there any other dataset types with the same mapping? todo add here
        openBisToRepoWiz.put("Q_EXPERIMENTAL_SETUP","condition")
    }

    def fillRepoWizMap(){
        repoWizToGeo = new HashMap()
        repoWizToGeo.put("design","")
        repoWizToGeo.put("source name","")
        repoWizToGeo.put("organism","")
        repoWizToGeo.put("molecule","")
        repoWizToGeo.put("instrument model","")
        repoWizToGeo.put("sequencing type","")
        repoWizToGeo.put("library protocol","")
        repoWizToGeo.put("ref genome","")
        repoWizToGeo.put("raw file","")
        repoWizToGeo.put("file name 1","file name 1")
        repoWizToGeo.put("file name 2","file name 2")
        repoWizToGeo.put("condition","condition")//keep it like that? add it to first hashmap?
    }
}
