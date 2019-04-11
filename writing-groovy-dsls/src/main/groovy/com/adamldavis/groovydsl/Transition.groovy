package com.adamldavis.groovydsl

import groovy.transform.CompileStatic

@CompileStatic
class Transition {

    static Transition transition(@DelegatesTo(Transition) Closure closure) {
        def transition = new Transition()
        closure.delegate = transition
        closure()
        transition
    }

    static List<Transition> transitions(@DelegatesTo(Transition) Closure ... closures) {
        closures.collect { transition it }
    }

    Map props = [:]

    Transition width(value) { props.width =   value; this }

    Transition linear(value) { props.linear = value; this }
}
