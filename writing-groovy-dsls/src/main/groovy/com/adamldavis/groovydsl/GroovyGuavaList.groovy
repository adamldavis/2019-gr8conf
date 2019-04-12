package com.adamldavis.groovydsl

import com.google.common.collect.ImmutableList
import groovy.transform.CompileStatic
import groovy.transform.Immutable

/** Groovy Guava List: has internal ImmutableList and implements operators. */
@Immutable(knownImmutableClasses = [ImmutableList])
@CompileStatic
class GroovyGuavaList<T> {

    final ImmutableList<T> list

    static <T1> GroovyGuavaList<T1> from(Iterable<T1> iterable) {
        new GroovyGuavaList<T1>( ImmutableList.copyOf(iterable as Iterable) as ImmutableList<T1> )
    }

    /** Implements this + object and returns a new instance. */
    GroovyGuavaList plus(T item) {
        new GroovyGuavaList<T>( ImmutableList.<T>builder().addAll(list).add(item).build() )
    }

    /** Implements this + another_GGList and returns a new instance. */
    GroovyGuavaList plus(GroovyGuavaList<T> other) {
        plus(other.list)
    }

    /** Implements this + any_list and returns a new instance. */
    GroovyGuavaList plus(List<T> other) {
        new GroovyGuavaList<T>( ImmutableList.<T>builder().addAll(list).addAll(other).build() )
    }

    /** Implements this << object and returns a new instance. */
    GroovyGuavaList leftShift(T item) {
        plus(item)
    }

    /** Implements this - object and returns a new instance. */
    GroovyGuavaList minus(T item) {
        new GroovyGuavaList<T>( ImmutableList.<T>builder().addAll(list.findAll {it != item}).build() )
    }

    /** Implements this[i] and returns object at given index. */
    T getAt(int index) {
        (T) list.get(index)
    }

    /** Returns the internal immutable list. */
    ImmutableList<T> get() { list }

    String toString() {
        list.toString()
    }

}
