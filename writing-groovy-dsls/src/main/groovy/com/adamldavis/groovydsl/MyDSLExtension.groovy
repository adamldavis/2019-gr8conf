package com.adamldavis.groovydsl

import groovy.transform.CompileStatic

/** Extension class that adds methods to existing classes through Extension Module. */
@CompileStatic
class MyDSLExtension {

    /**
     * Converts any Collection or Iterable to a GroovyGuavaList.
     *
     * @param iterable The list or set to convert.
     * @return A new {@link GroovyGuavaList} created from given collection.
     */
    static <T> GroovyGuavaList<T> toGGList(Iterable<T> iterable) {
        GroovyGuavaList.from(iterable)
    }


}
