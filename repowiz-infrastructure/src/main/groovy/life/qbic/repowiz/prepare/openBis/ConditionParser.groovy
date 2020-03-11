package life.qbic.repowiz.prepare.projectSearch.openBis


import life.qbic.xml.manager.StudyXMLParser
import life.qbic.xml.properties.Property
import life.qbic.xml.study.Qexperiment
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException

class ConditionParser {

    private static final Logger LOG = LogManager.getLogger(ConditionParser.class)

    StudyXMLParser studyParser = new StudyXMLParser()
    JAXBElement<Qexperiment> expDesign
    Map experimentalDesign

    ConditionParser(Map properties) {
        experimentalDesign = properties
    }

    List<Property> getSampleCondition(String sample) {
        studyParser.getFactorsAndPropertiesForSampleCode(expDesign, sample)
    }

    def parse() {
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
