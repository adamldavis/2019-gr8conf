
class Vampire {
    String name = ''
    int yearBorn
    //def setName(n) { println "setting $n"; name=n }
}
def vamp = new Vampire()

vamp.name = 'Dracula'
vamp.yearBorn = 1897

assert vamp.name == vamp.getName()
println '--'

println "name = $vamp.name"

def v2= new Vampire(name: 'Count Dracula', yearBorn: 1897)
println "v2 = $v2.name"
//TODO Print out the yearBorn as well

println "properties:"
println v2.properties // gets map of Properties


