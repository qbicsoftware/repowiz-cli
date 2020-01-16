package life.qbic.repowiz

import spock.lang.Specification

class MyFirstSpecification extends Specification {

    def "2 mulitiplied with 2 has to be 4"() {

        given:
        def myTestClass = new MyFirstTest()

        when:
        def number = 2
        def result = myTestClass.multiplyWithTwo(number)

        then:
        result == 4

    }



}