package com.adamldavis.groovydsl

import groovy.transform.CompileStatic


/**
 * Demos creating a static extension class.
 */
@CompileStatic
class MyStaticDSLExtension {

    static Dragon newDragon(Integer ignored, int age, String name) {
        new Dragon(age, name)
    }

    static Transition transition(Object ignored, @DelegatesTo(Transition) Closure closure) {
        CSS.transition closure
    }

}
