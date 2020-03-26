package life.qbic.repowiz.mapping

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class GeoMapper {

    private static final Logger LOG = LogManager.getLogger(GeoMapper.class)

    private HashMap geoTerms = new HashMap()
    private HashMap repoWizTerms = new HashMap()

    GeoMapper(){
        loadMetadataTerms()
    }

    String getRepoWizTerm(String geoTerm){
       return geoTerms.get(geoTerm)
    }

    String getGeoTerm(String repoWizTerm){
        return repoWizTerms.get(repoWizTerm)
    }

    def loadMetadataTerms(){
        InputStream stream = GeoMapper.class.getClassLoader().getResourceAsStream("META_INF/mapping/MetadataMapping.txt")
        stream.eachLine {line ->
            if(!line.startsWith('#')){
                List value = line.split(",")

                geoTerms.put(value[0],value[1])
                repoWizTerms.put(value[1],value[0])
            }
        }
    }

}
