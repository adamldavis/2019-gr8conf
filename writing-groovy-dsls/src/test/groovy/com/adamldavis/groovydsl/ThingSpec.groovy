package com.adamldavis.groovydsl

import spock.lang.Specification

class ThingSpec extends Specification {

    def "should override operators"() {
        expect:
        def t = new Thing(name: 't')
        def t2 = new Thing(name: 't2')
        def t3 = new Thing(name: 't3')

        assert "t<<t2" == "${t << t2}"
        assert "t + t2" == "${(t + t2).name}"
        assert "t - t3" == "${t - t3}"
        assert "t3~" == "${((~t3).name)}"

        t > t2
        assert "t > t2" == "$t2.name"

        t(t3) == "t call t3"
    }
}
