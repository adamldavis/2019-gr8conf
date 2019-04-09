
// ."string" notation
// can get properties and can call methods

def map = [:]
def x = 'spacex'

map."$x" = 42

println map

class Dragon {
    int capacity = 6
    def fly() {println "fly"}
}

def d = new Dragon()
def name = 'fly'
d."$name"()

