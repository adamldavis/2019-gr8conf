package com.adamldavis.groovydsl

import com.adamldavis.groovydsl.CommandChains.Task
import groovy.transform.CompileStatic

/** Pachyderm themed build tool. */
@CompileStatic
class Pachyderm extends Script {

    CommandChains project = new CommandChains()

    List<CommandChains.Task> getTasks() {
        project.tasks
    }

    /** Allows missing properties to be resolves as Tasks. */
    Object propertyMissing(String name) {
        if (project.tasks.any { it.name == name }) {
            return project.tasks.find { it.name == name }
        }
        return project.task(name)
    }

    /** Allows for "task anyName {}" syntax. */
    Object methodMissing(String name, Object args) {
        if (args instanceof Object[] && ((Object[]) args)[0] instanceof Closure) {
            return task(name, (Closure) ((Object[]) args)[0])
        }
        return propertyMissing(name)
    }

    CommandChains.Task task(String name, @DelegatesTo(CommandChains.Task.class) Closure closure) {
        CommandChains.Task task = project.task(name)
        closure.setDelegate(task)
        closure()
        return task
    }

    CommandChains.Task task(Task task) { task }

    @Override
    Object run() {
        return project
    }

    public static final ThreadLocal<Pachyderm> SINGLETON = new ThreadLocal<>();

    Pachyderm() { SINGLETON.set(this) }

}
