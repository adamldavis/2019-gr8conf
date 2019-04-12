package com.adamldavis.groovydsl


import spock.lang.Specification

/**
 * Demos using ComMand chain DSL to build a Gradle-like DSL.
 */
class GroovyGuavaListSpec extends Specification {

    def "should create GGList"() {
        expect:
        def list = [1,2,3]
        def ggList = GroovyGuavaList.from(list)

        ggList.get().size() == 3
        ggList[0] == 1
        ggList[1] == 2
        ggList[2] == 3
    }

    def "should add using plus or leftShit"() {
        when:
        def list = ['Iron Man', 'Captain America', 'Hulk', 'Doctor Strange', 'Thor']
        def ggList = GroovyGuavaList.from(list)

        def avengers = ggList + 'Black Widow' << 'Ant Man' << 'Black Panther'
        then:
        avengers[0] == 'Iron Man'
        avengers[5] == 'Black Widow'
        avengers[6] == 'Ant Man'
    }
    
    def "should add List using plus"() {
        when:
        def list = ['Iron Man', 'Captain America', 'Hulk', 'Doctor Strange', 'Thor']
        def ggList = GroovyGuavaList.from(list)
        
        def avengers = ggList + ['Black Widow', 'Ant Man', 'Black Panther']
        then:
        avengers[0] == 'Iron Man'
        avengers[5] == 'Black Widow'
        avengers[6] == 'Ant Man'
    }

    def "should subtract using Minus"() {
        when:
        def list = ['Iron Man', 'Captain America', 'Hulk', 'Doctor Strange']
        def ggList = GroovyGuavaList.from(list)
        def avengers = ggList - 'Doctor Strange' - 'Black Panther' // :(
        then:
        avengers.get().contains('Hulk')
        avengers.get().contains('Iron Man')
        false == avengers.get().contains('Doctor Strange')
        false == avengers.get().contains('Black Panther')
    }

}
