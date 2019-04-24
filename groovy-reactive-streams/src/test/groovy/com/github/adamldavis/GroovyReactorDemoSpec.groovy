package com.github.adamldavis

import spock.lang.Shared
import spock.lang.Specification

import java.util.stream.Collectors

import static com.github.adamldavis.DemoData.squares

class GroovyReactorDemoSpec extends Specification {


    @Shared
    GroovyReactorDemo demo = new GroovyReactorDemo()

    void "test DoSquares"() {
        expect:
        squares.toArray() == demo.doSquares().toArray()
    }

    void "test DoParallelSquares"() {
        expect:
        List result = demo.doParallelSquares()
                .stream().sorted().collect(Collectors.toList())

        squares.toArray() == result.toArray()
    }

}
