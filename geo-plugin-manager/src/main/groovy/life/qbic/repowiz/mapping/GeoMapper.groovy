package life.qbic.repowiz.mapping

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class GeoMapper {

    private static final Logger LOG = LogManager.getLogger(GeoMapper.class)

    private HashMap geoTermsToRepowiz = new HashMap()
    private HashMap repowizTermsToGeo = new HashMap()

    GeoMapper(){
        loadMetadataTerms()
    }

    String getRepoWizTerm(String geoTerm){
       return geoTermsToRepowiz.get(geoTerm)
    }

    String getGeoTerm(String repoWizTerm){
        return repowizTermsToGeo.get(repoWizTerm)
    }

    private def loadMetadataTerms(){
        InputStream stream = GeoMapper.class.getClassLoader().getResourceAsStream("mapping/MetadataMapping.txt")
        stream.eachLine {line ->
            if(!line.startsWith('#')){
                List value = line.split(",")

                geoTermsToRepowiz.put(value[0],value[1])
                repowizTermsToGeo.put(value[1],value[0])
            }
        }
    }

}
