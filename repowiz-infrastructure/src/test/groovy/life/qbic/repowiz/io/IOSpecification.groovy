package life.qbic.repowiz.io


import spock.lang.Specification

class IOSpecification extends Specification {

    /**def "finds all files in directory"(){
        when:
        def res = IO.getFilesFromDirectory("repositories/")

        then:
        res.size() == 3
    }*/

    def "parses file"(){
        given:
        def path = getClass().getResource("/repositories/geo.json").getPath()
        File file = new File(path)

        when:
        def res = JsonParser.parseFile(file)

        then:
        res.get("repositoryName") == "Geo"
    }
}
