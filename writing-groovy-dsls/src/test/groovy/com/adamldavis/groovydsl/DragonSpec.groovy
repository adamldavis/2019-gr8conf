package com.adamldavis.groovydsl

import static Dragon.createDragon

import spock.lang.Specification

class DragonSpec extends Specification {

    def "should create Dragon using @Builder strategy"() {
        expect:
        Dragon dragon = new Dragon(Dragon.createInitializer().age(181).name('Smaug'))

        dragon.name == 'Smaug'
        dragon.age == 181
    }

    def "should create Dragon using custom DSL and Command Chains"() {
        expect:
        Dragon dragon = Dragon.createDragon() withAge 181 named 'Smaug'

        dragon.name == 'Smaug'
        dragon.age == 181
    }

    def "should throw error if we forget age in custom DSL"() {
        when:
        Dragon.createDragon() named 'Oops'
        then:
        thrown(AssertionError)
    }

    def "should create Dragon using custom DSL with closure and Command Chain"() {
        expect:
        Dragon dragon = createDragon { withAge 181 named 'Smaug' }

        dragon.name == 'Smaug'
        dragon.age == 181
    }

}
