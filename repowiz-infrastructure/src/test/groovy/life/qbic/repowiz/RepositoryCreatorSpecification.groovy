package life.qbic.repowiz

import groovy.json.JsonOutput
import spock.lang.Specification

class RepositoryCreatorSpecification extends Specification {

    def "create a valid repository without exception"(){
        given:
        Map test = [repositoryName: "name",
        dataType: "type",
        uploadTypes: ["hts"],
        uploadFormat: "template",
        uploadRequirements: ["need valid identifier"],
        characteristics: [size: "size"],
        subsequentSteps: ["",""]]

        when:
        RepositoryCreator creator = new RepositoryCreator()
        Repository repo = creator.createRepository(test)
        print repo.repositoryName

        then:
        repo != null
    }

    def "create a valid repository with exception"(){
        given:
        Map test = [repositoryName: "name",
                    uploadTypes: ["hts"],
                    uploadFormat: "template",
                    uploadRequirements: ["need valid identifier"],
                    characteristics: [size: "size"],
                    subsequentSteps: ["",""]]

        when:
        RepositoryCreator creator = new RepositoryCreator()
        Repository repo = creator.createRepository(test)

        println new Repository().repositoryName

        then:
        repo != null
    }
}
