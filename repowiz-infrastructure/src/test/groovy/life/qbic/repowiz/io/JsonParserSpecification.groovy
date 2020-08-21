package life.qbic.repowiz.io


import spock.lang.Specification

class JsonParserSpecification extends Specification {

    def "parses file"() {
        given:
        //def path = getClass().getResource("/repositories/geo.json").getPath()
        //File file = new File(path)
        String file = "repositories/geo.json"
        InputStream stream = JsonParser.class.getClassLoader().getResourceAsStream(file)
        JsonParser parser = new JsonParser(stream)

        when:
        def res = parser.parse()


        then:
        res.get("repositoryName") == "Geo"
    }

    def "load schema successfully"() {
        given:
        JsonParser parser = new JsonParser(null)

        when:
        def schema = parser.getJsonSchemaFromClasspath("repositories/repository.schema.json")

        then:
        schema != null
    }

    def "parse schema successfully"() {
        given:
        InputStream stream = JsonParser.class.getClassLoader().getResourceAsStream("repositories/clinvar.json")
        JsonParser parser = new JsonParser(stream)

        def map = parser.parse()

        when:
        parser.validate("repositories/repository.schema.json", map)

        then:
        noExceptionThrown()
    }

    def "throw exception for wrong json data"() {
        given:
        InputStream stream = JsonParser.class.getClassLoader().getResourceAsStream("repositories/invalidRepository.json")
        JsonParser parser = new JsonParser(stream)
        def map = parser.parse()

        when:
        parser.validate("repositories/repository.schema.json", map)


        then:
        thrown IllegalArgumentException
    }

    def "validate integrity number"() {
        given:
        def map = ["integrity number": "9.1"]

        when:
        JsonParser.validate("metadataMapping/RepoWizSample.schema.json", map)

        then:
        noExceptionThrown()
    }
}
