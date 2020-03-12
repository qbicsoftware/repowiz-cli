package life.qbic.repowiz


//todo move this stuff to external repository
class TemporaryDatabase {

    HashMap openBisToRepoWiz
    HashMap repoWizToGeo
    HashMap geoToRepoWiz


    List repoWiz = ["project title", "summary","design","contributor","supplementary file","SRA_center_name_code", "sample name","sample title",
                    "source name","organism","characteristics: tag","molecule","sample description", "processed data file","raw file","growth protocol","treatment protocol",
                    "extract protocol","library protocol","sequencing type","data processing step","ref genome","processed data files format and content","processed file","processed file",
                    "processed file checksum","raw file","raw file","raw file checksum","instrument model","read length","sequencing mode","file name 1","file name 2","average insert size",
                    "standard deviation"]

    List geo = ["series_title", "series_summary","series_overall design","series_contributor","series_supplementary file","series_SRA_center_name_code","samples_Sample name","samples_title","samples_source name",
                "samples_organism","samples_characteristics: tag", "samples_molecule","samples_description","samples_processed data file","samples_raw file","protocols_growth protocol","protocols_treatment protocol","protocols_extract protocol",
                "protocols_library construction protocol","protocols_library strategy","data processing pipeline_data processing step","data processing pipeline_genome build","data processing pipeline_processed data files format and content", "processed data files_file name","processed data files_file type",
                "processed data files_file checksum", "raw files_file name","raw files_file type","raw files_file checksum","raw files_instrument model","raw files_read length","raw files_single or paired-end","paired-end experiments_file name 1","paired-end experiments_file name 2","paired-end experiments_average insert size",
                "paired-end experiments_standard deviation"]

    TemporaryDatabase(){
        fillOpenBisMap()
        fillGeoWizMap()
    }

    def fillOpenBisMap(){
        openBisToRepoWiz = new HashMap()
        openBisToRepoWiz.put("Q_PROJECT_DETAILS","series_design")
        openBisToRepoWiz.put("Q_PRIMARY_TISSUE","samples_source name")
        openBisToRepoWiz.put("Q_NCBI_ORGANISM","samples_organism")
        openBisToRepoWiz.put("Q_SAMPLE_TYPE","samples_molecule")
        openBisToRepoWiz.put("Q_SEQUENCER_DEVICE","raw files_instrument model")
        openBisToRepoWiz.put("Q_SEQUENCING_MODE","raw files_sequencing mode")
        openBisToRepoWiz.put("Q_SEQUENCING_TYPE","raw files_sequencing type")
        openBisToRepoWiz.put("Q_ADDITIONAL_INFO_SAMPLE","protocols_library protocol")
        openBisToRepoWiz.put("Q_ADDITIONAL_INFO_EXPERIMENT","protocols_ref genome")
        openBisToRepoWiz.put("Q_NGS_RAW_DATA","samples_raw file") //are there any other dataset types with the same mapping? todo add here
        openBisToRepoWiz.put("Q_EXPERIMENTAL_SETUP","samples_characteristics")
    }

    def fillGeoWizMap(){
        repoWizToGeo = new HashMap()
        geoToRepoWiz = new HashMap()


        for(int pos = 0; pos < geo.size(); pos++){
            repoWizToGeo.put(repoWiz[pos],geo[pos])
            geoToRepoWiz.put(geo[pos],repoWiz[pos])
        }


    }
}
