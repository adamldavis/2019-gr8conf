package com.adamldavis.groovydsl

import spock.lang.Specification

class TransitionSpec extends Specification {

    def "should create Transition using DSL"() {
        expect:
        def t = CSS.transition { width 2 linear 1 }

        t.property == 'width'
        t.duration == 2
        t.timing == 'linear'
        t.delay == 1
    }

    def "should create many Transitions using DSL"() {
        expect:
        def list = CSS.transitions
                { width 2 linear 1 }{ height 2 ease 1 }

        list.size() == 2
        def t = list[0]
        t.property == 'width'
        t.duration == 2
        t.timing == 'linear'
        t.delay == 1
    }

}
