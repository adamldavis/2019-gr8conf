
// implementing operators

class Thing implements Comparable {
    String name
    
    def leftShift(Thing t) {
        new Thing(name: name + "<<" + t.name)
    }
    def plus(Thing t) {
        new Thing(name: name + " ++ " + t.name) 
    }
    def minus(Thing t) {
        new Thing(name: name + " -- " + t.name) 
    }
    Thing bitwiseNegate() { new Thing(name: name + "~") }
    
    def call(Thing other) {
        println "call $other"
        this
    }
    String toString() {name}
    
    int compareTo(other) {
        other.name = name + ' > ' + other.name
        1
    }
}

def t = new Thing(name: 't')
def t2 = new Thing(name: 't2')
def t3 = new Thing(name: 't3')

println "t<<t2 :: ${t << t2}"
println "t+t2 :: ${(t + t2).name}"
println "t-t3 :: ${t - t3}"
println "~t3 :: ${((~t3).name)}"

t > t2
println "compareTo :: $t2.name"

t(t3)


