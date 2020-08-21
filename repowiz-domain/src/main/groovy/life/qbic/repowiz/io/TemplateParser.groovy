package life.qbic.repowiz.io

/**
 * Interface determining how a template is parsed
 *
 * The TemplateParser interface provides methods for template file parsing.
 *
 *  @since: 1.0.0
 *  @author: Jennifer Bödker
 *
 */
interface TemplateParser {

    /**
     * Function to create a workbook from a given filename
     * @param fileName
     */
    def createWorkbook(String fileName)

}
