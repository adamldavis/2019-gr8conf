package com.adamldavis.groovydsl

import groovy.transform.CompileStatic

/** Pachyderm themed build tool. */
@CompileStatic
class Pachyderm extends Script {

    CommandChains project = new CommandChains()

    List<CommandChains.Task> getTasks() {
        project.tasks
    }

    Object propertyMissing(String name) {
        return project.task(name)
    }

    Object task(String name, @DelegatesTo(CommandChains.Task.class) Closure closure) {
        Object task = project.task(name)
        closure.setDelegate(task)
        closure()
        return task
    }

    @Override
    Object run() {
        return project
    }

}
