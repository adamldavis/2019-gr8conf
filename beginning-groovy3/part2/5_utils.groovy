// Utilities

//The GDK adds several utility classes such as ConfigSlurper, Expando, and ObservableList/Map/Set.

// ConfigSlurper

/*ConfigSlurper is a utility class for reading configuration files defined in the form of Groovy scripts.
Like with Java *.properties files, ConfigSlurper allows a dot notation.
It also allows for nested (Closure) configuration values and arbitrary object types.*/

    def config = new ConfigSlurper().parse('''
        app.date = new Date()
        app.age  = 42
        app {
            name = "Test${42}"
        }
    ''')

    def properties = config.toProperties()

    assert properties."app.date" instanceof String
    assert properties."app.age" == '42'
    assert properties."app.name" == 'Test42'
    
    println properties

// Expando

/*The Expando class can be used to create a dynamically expandable object.*/

    def expando = new Expando()
    expando.name = 'Draco'
    expando.say = { String s -> "${expando.name} says: $s" }
    println expando.say('hello') // Draco says: hello

// Use meta-programming to alter some class's `metaClass` and then print out the class of the `metaClass`. Is it the `Expando` class?

