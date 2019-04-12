package com.adamldavis.groovydsl

/**
 * CSS DSL; demos using one or multiple Closures as parameters and using delegate.
 */
class CSS {

    /** Creates a Transition from given Closure. */
    static Transition transition(@DelegatesTo(Transition) Closure closure) {
        def transition = new Transition()
        closure.delegate = transition
        closure()
        transition
    }

    /** Takes multiple closures and converts them to Transitions. */
    static List<Transition> transitions(@DelegatesTo(Transition) Closure ... closures) {
        closures.collect { transition it }
    }

}
