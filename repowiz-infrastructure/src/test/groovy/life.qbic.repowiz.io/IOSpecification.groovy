package life.qbic.repowiz.io

import life.qbic.repowiz.utils.IO
import spock.lang.Specification

class IOSpecification extends Specification {

    def "finds all files in directory"(){
        given:
        InputStream dirURL = getClass().getResourceAsStream("/repositories")

        when:
        def res = IO.getFilesFromDirectory(dirURL)

        then:
        res.size() == 3
    }

    def "parses file"(){
        given:
        def path = getClass().getResource("/repositories/geo.json")
        File file = new File(path.toURI())

        when:
        def res = IO.parseJSON(file)

        then:
        res.get("repositoryName") == "Geo"
    }
}
