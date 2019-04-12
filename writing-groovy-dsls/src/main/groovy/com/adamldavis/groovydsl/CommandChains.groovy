package com.adamldavis.groovydsl

import groovy.transform.CompileStatic
import groovy.transform.MapConstructor
import groovy.transform.NamedParam
import groovy.transform.NamedParams
import groovy.transform.ToString

/**
 * Demos Command Chains in Groovy. Although this is a Groovy class, it could be just a Java class.
 */
@CompileStatic
class CommandChains {

    /** For this example we'll use a Gradle-like Task. */
    @MapConstructor
    @ToString(excludes = ['doLast', 'doFirst'])
    static class Task {
        String name
        Closure doLast
        Closure doFirst
        List<String> depends = []
        Class type
        Task doLast(Closure doLast) { this.doLast = doLast; this }
        Task doFirst(Closure doFirst) { this.doFirst = doFirst; this }
        Task dependsOn(String ... depends) { this.depends.addAll(depends); this }
        Task type(Class type) { this.type = type; this }
    }

    List<Task> tasks = []

    /** Command chains typically involve methods with one parameter that return the next object in the chain. */
    Task task(String taskName) {
        def task = new Task(name: taskName)
        tasks << task
        task
    }

    /** Groovy allows special syntax for a Map parameter which allows:
     * <code>task key: value, key2: value2</code>.
     */
    Task task(@NamedParams([ // NamedParams describes the possible named parameters
        @NamedParam(value = "name", type = String.class),
        @NamedParam(value = "type", type = Class.class),
        @NamedParam(value = "doLast", type = Closure.class),
        @NamedParam(value = "doFirst", type = Closure.class),
        @NamedParam(value = "depends", type = List.class)
    ]) Map<String, Object> map)
    {
        def task = new Task(map)
        tasks << task
        task
    }
}
/** Empty class just for demo. */
class TestTask {}

/** Empty class just for demo. */
class JavacTask {}
