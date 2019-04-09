
def incr = {x -> x + 1}
println( 'incr(2)=' + incr(2) )

//println(incr.class)
//def incr = {it + 1}

println '\n\n collect:'

def list = ['foo','bar']
def newSet = new HashSet()
list.collect( newSet ) { it.toUpperCase() }
println newSet //  ["FOO", "BAR"]
println newSet.class

println "\n\n find:"

def find = {collection, tester ->
    for (item in collection)
        if (tester(item)) return item
}

def found = find([10, 11, 12]) { it > 10 }
println "found: $found"
println ""
println "gdk: ${[10, 11, 12].find {it > 10}}"

def x = []?.size()
println x



println '\n\n delegate:'

class Bar { String name() { "BAR" } }

def close = { println "close ${name()}" }
close.delegate = new Bar()
// methods of delegate are in scope
close()

