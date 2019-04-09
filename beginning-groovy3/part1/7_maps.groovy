
def map = [cars: 1, boats: 2, planes: 3]

println map
println "cars = $map.cars"
println "boats = $map.boats"

map.cars = 42
println "cars = $map.cars"

def key = "rockets"
def map2 = [(key): 5]

println "map2=$map2"

def all = map + map2

println "all=$all"

