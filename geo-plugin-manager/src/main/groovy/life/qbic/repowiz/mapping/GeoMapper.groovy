package life.qbic.repowiz.mapping

import life.qbic.repowiz.io.JsonParser
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class GeoMapper {

    private static final Logger LOG = LogManager.getLogger(GeoMapper.class)

    private HashMap geoTermsToRepowiz = new HashMap()
    private HashMap repowizTermsToGeo = new HashMap()

    GeoMapper() {
        loadMetadataTerms()
    }

    String getRepoWizTerm(String geoTerm) {
        if (geoTerm.contains("characteristics")) {
            String label = geoTerm.split(" ")[1]
            return "characteristic " + label
        }
        return geoTermsToRepowiz.get(geoTerm)
    }

    String getGeoTerm(String repoWizTerm) {
        if (repoWizTerm.contains("characteristic")) {
            String label = repoWizTerm.split(" ")[1]
            return "characteristics: " + label
        }
        return repowizTermsToGeo.get(repoWizTerm)
    }

    private def loadMetadataTerms() {
        InputStream stream = GeoMapper.class.getClassLoader().getResourceAsStream("mapping/geoMapping.json")
        JsonParser parser = new JsonParser(stream)

        repowizTermsToGeo = (HashMap) parser.parse()

        repowizTermsToGeo.keySet().each { key ->
            geoTermsToRepowiz.put(repowizTermsToGeo.get(key), key.toString())
        }
    }
}
