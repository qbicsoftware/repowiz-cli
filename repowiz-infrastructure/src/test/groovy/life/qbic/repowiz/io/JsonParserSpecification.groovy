package life.qbic.repowiz.io

import spock.lang.Specification

class JsonParserSpecification extends Specification {

    /**def "finds all files in directory"(){
        when:
        def res = IO.getFilesFromDirectory("repositories/")

        then:
        res.size() == 3
    }*/

    def "parses file"(){
        given:
        //def path = getClass().getResource("/repositories/geo.json").getPath()
        //File file = new File(path)
        JsonParser parser = new JsonParser()

        when:
        def res = parser.parseAsFile("repositories/geo.json")

        then:
        res.get("repositoryName") == "Geo"
    }
}
