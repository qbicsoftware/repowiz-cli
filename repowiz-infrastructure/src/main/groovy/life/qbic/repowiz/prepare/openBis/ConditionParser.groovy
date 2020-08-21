package life.qbic.repowiz.prepare.openBis

import life.qbic.xml.manager.StudyXMLParser
import life.qbic.xml.properties.Property
import life.qbic.xml.study.Qexperiment
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException

/**
 * This class is responsible for parsing experimental conditions from openbis
 *
 * The conditions are stored as XML in the experimental design. This class allows to translate the conditions onto the sample level
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
class ConditionParser {

    private static final Logger LOG = LogManager.getLogger(ConditionParser.class)

    private StudyXMLParser studyParser = new StudyXMLParser()
    private JAXBElement<Qexperiment> expDesign
    private Map experimentalDesign

    /**
     * Creates a ConditionParser for a Map with properties
     * @param properties contain the information of the experimental design
     */
    ConditionParser(Map properties) {
        experimentalDesign = properties
    }

    /**
     * Retrieves the condition for a given sample
     * @param sample described by its code
     * @return the conditions found for the sample
     */
    List<Property> getSampleCondition(String sample) {
        studyParser.getFactorsAndPropertiesForSampleCode(expDesign, sample)
    }

    /**
     * Parses the experimental conditions stored in the experimental design
     */
    void parse() {
        String xmlString = experimentalDesign.get("Q_EXPERIMENTAL_SETUP")

        try {
            LOG.info "Parsing experiment conditions"
            expDesign = studyParser.parseXMLString(xmlString)
        }
        catch (JAXBException e) {
            LOG.info "Could not create new experimental design xml from experiment."
            //e.printStackTrace()
        }
    }
}
