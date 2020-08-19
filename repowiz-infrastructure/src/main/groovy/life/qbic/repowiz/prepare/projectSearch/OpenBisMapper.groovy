package life.qbic.repowiz.prepare.projectSearch

import life.qbic.xml.properties.Property
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * This class handles how data model is mapped from OpenBis to the RepoWiz data model
 *
 * In order to handle the metadata terms of OpenBis they must be mapped so that they can be translated into
 * RepoWiz terms. This class implements the {@link Mapper} interface.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class OpenBisMapper implements Mapper {

    private final Map translateToRepoWiz

    private static final Logger LOG = LogManager.getLogger(OpenBisMapper.class)

    /**
     * Creates a mapper for OpenBis
     *
     * @param data is a map containing information on the translation of OpenBis to RepoWiz terms
     */
    OpenBisMapper(Map data) {
        translateToRepoWiz = data
    }

    /**
     * Maps the OpenBis properties within a map onto a map of RwpoWiz terms
     *
     * @param properties of a project from OpenBis
     * @return a map containing the translated properties
     */
    HashMap mapProperties(Map properties) {
        HashMap repoWizTerms = new HashMap()

        properties.each { key, value ->
            String repoWizTerm = translateToRepoWiz.get(key)

            if (key == "Q_SEQUENCER_DEVICE") value = getSequencingDevice(value.toString())
            if (repoWizTerm != null) repoWizTerms.put(repoWizTerm, value.toString().trim())
        }
        return repoWizTerms
    }

    /**
     * Creates a map entry for a list of files of a dataset
     *
     * @param files as a list of filenames from openbis
     * @param dataSetType is the dataset type in OpenBis
     * @return a map of size 1 containing the files
     */
    HashMap mapFiles(List files, String dataSetType) {
        //keep all files in a list and separate them later on (cannot keep duplicate keys in hashmap)
        return [(translateToRepoWiz.get(dataSetType)): files]
    }

    /**
     * Maps properties which contain experimental conditions.
     *
     * @param properties as a list of properties of a project
     * @return a map with the properties splitted into label as key and value as value
     */
    HashMap mapConditions(List<Property> properties) {
        HashMap map = new HashMap()
        String repoWizTerm = translateToRepoWiz.get("Q_EXPERIMENTAL_SETUP") //todo do not hardcode??

        if (properties != null) {
            properties.each { sampleProp ->
                String value = sampleProp.value.trim()
                String label = sampleProp.label

                //if(sampleProp.unit) //todo what if there is a unit?
                map.put(repoWizTerm + " " + label, value)
            }
        }
        return map
    }

    /**
     * Masks duplicate properties by adding the sample type in front of the property.
     * Masked are currently only samples of the type Q_SECONDARY_NAME and Q_ADDITIONAL_INFO
     *
     * @param type describes the sample typ of the sample which contains the property
     * @param properties as a map containing a property which may needs to be masked
     * @return a map containing the same properties but some of them might be masked
     */
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

    /**
     * Extracts the name of the sequencing device
     *
     * @param device as a string describing the device and its location
     * @return the device name without information on its location
     */
    String getSequencingDevice(String device) {
        if (device.contains("at")) {
            String[] model = device.split("at")
            return model[0]
        }
        return device
    }
}
