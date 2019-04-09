println '''
    REGEX = REGULAR EXPRESSIONS
    '''
println "----------------REGEX using ==~"
println "Java style::"

println("Mercury".matches("\\w+"))

println "Groovy style::"

if ("Mercury" ==~ /\w+/) println "matched!"

//if (email ==~ /[\w.]+@[\w.]+/

println "----------------REGEX in find/any/every"

def names = ['Mercury', 'Gemini', 'Apollo', 'Curiosity', 'Phoenix']
println """
    names=$names
    """
// =~ means find anywhere
println("any: " + names.any { it =~ /r/ })
// ==~ means match the whole string
println("every: " + names.every { it ==~ /[A-Z][a-z]+/ })
println("find: " + names.find { it ==~ 'Apol+o' })

println("Start with C: " + names.findIndexOf { name -> name =~ /^C.*/ })

println("Contains y or Y: " + names.findIndexValues { it =~ /(y|Y)/ })

def props = [name: 'Groovy', url: 'http://groovy-lang.org']
println """
    props=$props
    """
def found = props.find { it.value =~ /[Gg]roovy/ }
def all = props.findAll { it.value =~ /[Gg]roovy/ }

println "found:  $found    findAll: $all \n"

//println "${all.getClass()}"

// REGEX in switch
println "----------------REGEX in SWITCH"
def test = {value ->
    switch (value) {
        case ~/Ja.+/: return 'java'
        case ~/Gr.+/: return 'groovy'
        default: return 404
    }
}
println test("Java")
println test("Groovy")
println test("Ruby")

