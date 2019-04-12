package com.adamldavis.groovydsl


import groovy.transform.Immutable
import groovy.transform.TypeChecked
import groovy.transform.builder.Builder
import groovy.transform.builder.InitializerStrategy

@Immutable   // ADDS toString, equals, hashCode, MapConstructor, TupleConstructor
            // makes everything final
@TypeChecked
@Builder(builderStrategy = InitializerStrategy) // built-in Groovy AST transformation
        // allows this syntax: new Dragon(Dragon.createInitializer().age(1000).name('Smaug'))
class Dragon {

    /** Custom builder method. */
    static DragonBuilder createDragon() { new DragonBuilder() }

    /** Custom builder with Closure using the DragonBuilder as a delegate.
     * @DelegatesTo helps the IDE figure out what's going on allowing for code completion.
     */
    static Dragon createDragon(@DelegatesTo(value = DragonBuilder, strategy = Closure.DELEGATE_FIRST)
                                 Closure<Dragon> closure) {
        def builder = new DragonBuilder()
        closure.resolveStrategy = Closure.DELEGATE_FIRST
        closure.delegate = builder
        closure()
    }

    int age
    String name

    static class DragonBuilder { //custom builder DSL
        int age

        DragonBuilder withAge(int age) { this.age = age; this }

        Dragon named(String name) {
            assert age
            assert name
            new Dragon(age, name)
        }
    }

}
