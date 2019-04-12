package com.adamldavis.groovydsl

import spock.lang.Specification

class ExtensionSpec extends Specification {


    def "should convert any List to a GGList using toGGList"() {
        expect:
        ['foo', 'bar'].toGGList() instanceof GroovyGuavaList
    }

    def "should create Dragon from Integer class extension"() {
        expect:
        Integer.newDragon(181, 'Smaug') instanceof Dragon
    }

}
