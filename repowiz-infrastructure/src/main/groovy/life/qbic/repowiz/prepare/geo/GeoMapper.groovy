package life.qbic.repowiz.prepare.projectSearch.geo

import life.qbic.repowiz.prepare.RepositoryMapper
import life.qbic.repowiz.prepare.projectSearch.TemporaryDatabase
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class GeoMapper implements RepositoryMapper{

    private static final Logger LOG = LogManager.getLogger(GeoMapper.class)

    HashMap toGeo = new HashMap()
    HashMap toRepoWiz = new HashMap()


    GeoMapper(String uploadType){
        TemporaryDatabase temp = new TemporaryDatabase()

        if(uploadType == "hts"){
            toRepoWiz = temp.repoWizToGeo
            toGeo = temp.geoToRepoWiz
        }
    }

    @Override
    String mapPropertiesToRepoWiz(String prop){
        /*List repoWizTerms = []

        properties.each {property ->
            String repoWizTerm = toGeo.get(property)
            if (repoWizTerm != null) repoWizTerms << repoWizTerm
        }

        return repoWizTerms*/
        if(toGeo.get(prop) == null) LOG.debug prop

        return toGeo.get(prop)
    }

    @Override
    String mapPropertiesToOutput(String properties){
        /*HashMap repoWizTerms = new HashMap()

        properties.each {key, value ->
            String repoWizTerm = toGeo.get(key)
            if (repoWizTerm != null) repoWizTerms.put(repoWizTerm, value)
        }*/
        return ""
    }

}
