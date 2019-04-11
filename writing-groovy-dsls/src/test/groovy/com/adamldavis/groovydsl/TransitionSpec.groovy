package com.adamldavis.groovydsl

import spock.lang.Specification

class TransitionSpec extends Specification {

    def "should create Transition using DSL"() {
        expect:
        def t = Transition.transition { width 2 linear 1 }

        t.props.width == 2
        t.props.linear == 1
    }

}
