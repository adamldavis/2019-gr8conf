
// ONLY WORKS IN GROOVY 3!

// LAMBDAS

def lambda = (x,date2) -> {x + date2}
def section = x -> println("-"*30 + x)

def square = x -> x*x

section "Lambdas"
println "2^2 = ${square(2)}"

// METHOD REF

section "Method Refs"
def add1(x) { x + 1 }

def groovyStyle = (1..12).collect( this.&add1 )

println "groovyStyle=$groovyStyle"

def javaStyle = (1..12).collect( this::add1 ) // this.&add1

println "javaStyle=$javaStyle"

// Constructor REF
section "Constructor Refs"
def newDate = Date::new
def dates = (1..12).collect(newDate)

println "dates.size=${dates.size}"


