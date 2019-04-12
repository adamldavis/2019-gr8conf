package com.adamldavis.groovydsl


import spock.lang.Specification

/**
 * Demos using Command chain DSL to build a Gradle-like DSL.
 */
class CommandChainSpec extends Specification {

    def "should create Task using DSL"() {
        expect:
        def cc = new CommandChains()
        with(cc) {
            task 'build'
        }
        cc.tasks.size() == 1
        cc.tasks[0].name == 'build'
    }

    def "should create Tasks using DSL with command chains"() {
        expect:
        def cc = new CommandChains()
        with(cc) {
            task 'test' type TestTask doLast { println 'done' }
            task 'build' type JavacTask dependsOn 'clean', 'test'
        }
        cc.tasks.size() == 2
        cc.tasks[0].name == 'test'
        cc.tasks[0].type == TestTask
        cc.tasks[1].name == 'build'
        cc.tasks[1].type == JavacTask
    }

    def "should create Tasks using DSL with map"() {
        expect:
        def cc = new CommandChains()
        with(cc) {
            task name: 'test', type: TestTask, doLast: { println 'done' }
            task name: 'build', type: JavacTask, depends: ['clean', 'test']
        }
        cc.tasks.size() == 2
        cc.tasks[0].name == 'test'
        cc.tasks[0].type == TestTask
        cc.tasks[1].name == 'build'
        cc.tasks[1].type == JavacTask
    }


}
