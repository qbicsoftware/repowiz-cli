package life.qbic.repowiz.prepare.projectSearch

import ch.ethz.sis.openbis.generic.asapi.v3.dto.experiment.Experiment
import ch.ethz.sis.openbis.generic.asapi.v3.dto.sample.Sample
import life.qbic.repowiz.prepare.model.RepoWizProject
import life.qbic.xml.properties.Property
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Mapp {

    final HashMap toRepoWiz

    private static final Logger LOG = LogManager.getLogger(Mapp.class)


    Mapp(){
        TemporaryDatabase temp = new TemporaryDatabase()
        toRepoWiz = temp.openBisToRepoWiz
    }

    HashMap maskProperties(HashMap properties){
        HashMap repoWizTerms = new HashMap()

        properties.each {key, value ->
            String repoWizTerm = toRepoWiz.get(key)
            if (repoWizTerm != null) repoWizTerms.put(repoWizTerm, value)
        }
        return repoWizTerms
    }

    HashMap maskFiles(List files, String dataSetType){
        //keep all files in a list and separate them later on (cannot keep duplicate keys in hashmap)
        return [ (toRepoWiz.get(dataSetType)) : files]
    }

    HashMap maskConditions(List<Property> properties){
        HashMap map = new HashMap()
        String repoWizTerm = toRepoWiz.get("Q_EXPERIMENTAL_SETUP") //todo do not hardcode??

        if (properties != null) {
            properties.each { sampleProp ->
                String value = sampleProp.value
                String label = sampleProp.label
                map.put(repoWizTerm + " " + label + ":", value)
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
