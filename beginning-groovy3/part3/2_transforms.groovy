// TRANSFORMS
import groovy.transform.*

@ToString
@TupleConstructor
@EqualsAndHashCode
class Person {
    def name
    int age
}
Person bob = new Person("Bob", 35)
println "person = $bob" //toString


Person bob2 = new Person("Bob", 35)

println "\n\n\n"
println "bob == bob2? ${bob == bob2}" //equals
println "\n\n\n"

//*/
@Canonical     // ADDS TOSTRING, EQUALS, HASHCODE, CONSTRUCTOR
//@TypeChecked
class Dragon {
    final age
    String name
    List<String> names = []
}
Dragon dragon = new Dragon()

def d = new Dragon(1000, "Smaug")
println "$d.name is $d.age years old"

def norbort = new Dragon(1, "Norbort")
println "$norbort.name is $norbort.age year old"

//d.age = 2
println ''
println norbort.toString()

def d2 = new Dragon(2, "Smaug")
def d3 = new Dragon(1000, "Smaug")
println "\n d2equal? ${d == d2}"
println "\n d3equal? ${d == d3}"


//*/

