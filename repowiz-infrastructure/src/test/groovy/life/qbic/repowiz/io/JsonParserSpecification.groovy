package life.qbic.repowiz.io

import com.networknt.schema.JsonSchema
import groovy.json.JsonOutput
import life.qbic.repowiz.Repository
import life.qbic.repowiz.RepositoryCreator
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

    def "load schema successfully"(){
        given:
        JsonParser parser = new JsonParser()

        when:
        def schema = parser.getJsonSchemaFromClasspath("repositories/repository.schema.json")

        then:
        schema != null
    }

    def "parse schema successfully"(){
        given:
        JsonParser parser = new JsonParser()
        parser.getMapFromJsonFile("repositories/clinvar.json")

        when:
        parser.validate("repositories/repository.schema.json")

        then:
        noExceptionThrown()
    }

    def "throw exception for wrong json data"(){
        given:
        JsonParser parser = new JsonParser()
        parser.getMapFromJsonFile("repositories/invalidRepository.json")

        when:
        parser.validate("repositories/repository.schema.json")


        then:
        thrown IllegalArgumentException
    }
}
