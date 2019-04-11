package com.adamldavis.groovydsl

/**
 * Demos implementing operators in Groovy.
 */
class Thing implements Comparable {
    String name

    def leftShift(Thing t) {
        new Thing(name: name + "<<" + t.name)
    }
    def plus(Thing t) {
        new Thing(name: name + " ++ " + t.name)
    }
    def minus(Thing t) {
        new Thing(name: name + " -- " + t.name)
    }
    Thing bitwiseNegate() { new Thing(name: name + "~") }

    def call(Thing other) {
        println "call $other"
        this
    }
    String toString() {name}

    int compareTo(other) {
        other.name = name + ' > ' + other.name
        1
    }
}
