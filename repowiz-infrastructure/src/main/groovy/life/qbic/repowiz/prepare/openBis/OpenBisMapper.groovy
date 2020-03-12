package life.qbic.repowiz.prepare.openBis

import life.qbic.repowiz.prepare.projectSearch.LocalDatabaseMapper
import life.qbic.repowiz.TemporaryDatabase
import life.qbic.xml.properties.Property
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class OpenBisMapper implements LocalDatabaseMapper{

    //todo add interface for generalization
    final HashMap toRepoWiz

    private static final Logger LOG = LogManager.getLogger(OpenBisMapper.class)

    OpenBisMapper(){
        TemporaryDatabase temp = new TemporaryDatabase()
        toRepoWiz = temp.openBisToRepoWiz
    }

    HashMap mapProperties(Map properties){
        HashMap repoWizTerms = new HashMap()

        properties.each {key, value ->
            String repoWizTerm = toRepoWiz.get(key)
            if (repoWizTerm != null) repoWizTerms.put(repoWizTerm, value)
        }
        return repoWizTerms
    }

    HashMap mapFiles(List files, String dataSetType){
        //keep all files in a list and separate them later on (cannot keep duplicate keys in hashmap)
        return [ (toRepoWiz.get(dataSetType)) : files]
    }

    HashMap mapConditions(List<Property> properties){
        HashMap map = new HashMap()
        String repoWizTerm = toRepoWiz.get("Q_EXPERIMENTAL_SETUP") //todo do not hardcode??

        if (properties != null) {
            properties.each { sampleProp ->
                String value = sampleProp.value
                String label = sampleProp.label

                //if(sampleProp.unit) //todo what if there is a unit?
                map.put(repoWizTerm + " " + label, value)
            }
        }
        return map
    }

    HashMap maskDuplicateProperties(String type, Map properties) {
        HashMap masked = new HashMap()

        properties.each { key, value ->
            if (key == "Q_SECONDARY_NAME") masked.put("Q_SECONDARY_NAME_" + type, value)
            else if (key == "Q_ADDITIONAL_INFO") masked.put("Q_ADDITIONAL_INFO_" + type, value)
            else {
                masked.put(key, value)
            }
        }
        return masked
    }
}
