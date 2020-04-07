package life.qbic.repowiz

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
        RepositoryCreator creator = new RepositoryCreator(test)
        Repository repo = creator.create()

        then:
        repo != null
    }

}
