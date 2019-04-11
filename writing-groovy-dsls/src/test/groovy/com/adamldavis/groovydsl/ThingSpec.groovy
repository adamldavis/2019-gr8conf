package com.adamldavis.groovydsl

import spock.lang.Specification

class ThingSpec extends Specification {

    def "should override operators"() {
        expect:
        def t = new Thing(name: 't')
        def t2 = new Thing(name: 't2')
        def t3 = new Thing(name: 't3')

        println "t<<t2 :: ${t << t2}"
        println "t+t2 :: ${(t + t2).name}"
        println "t-t3 :: ${t - t3}"
        println "~t3 :: ${((~t3).name)}"

        t > t2
        println "compareTo :: $t2.name"

        t(t3)
    }
}
