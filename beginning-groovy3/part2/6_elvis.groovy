//class
class Person {String name}

// Java style
String name
Person p = new Person(name: "Bob")
// Safe dereference
name = p == null ? null : p.getName()
// Groovy Safe dereference
name = p?.name

// Java
name = p.getName() == null ? "none" : p.getName()
// Groovy Elvis operator
name = p.name ?: "none"

// both Java
name = (p == null ? null : p.getName()) == null ? "none" : p.getName()
// both Groovy
//TODO convert the above code the Groovy eqivalent
println "name=$name"

