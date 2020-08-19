package life.qbic.repowiz.cli

import spock.lang.Specification

class SubmissionPresenterSpecification extends Specification {

    def "test"() {
        given:
        String formattedChoices = "Please choose one of the following options: \n"
        List choices = ["hi", "hallo", "gallo"]

        when:
        choices.each {
            formattedChoices += "$it "
        }

        print formattedChoices

        then:
        formattedChoices == "Please choose one of the following options: \nhi hallo gallo "

    }
}
