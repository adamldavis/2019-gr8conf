import groovy.transform.TupleConstructor

@TupleConstructor
class Vampire {
    String name
    int yearBorn
    String toString() { name }
}

ARRAYS: {
    Vampire[] vampires = new Vampire[10]; // Vampire array with length 10
    String[] names = ["Dracula", "Edward"];
    println(vampires)
    println(names)
}
println "LISTS"
LISTS: {
    // java way
    List<Vampire> vampires = new ArrayList();
    vampires.add(new Vampire("Count Dracula", 1897))
    println(vampires)

    //groovy way
    def list = []
    println (list.class)
    list.add(new Vampire("Count Dracula", 1897))
    // or
    list << new Vampire("Count Dracula", 1897)
    // or
    list += new Vampire("Count Dracula", 1897)
    
    println(list)
}
println "SETS"
SET1: {
    def dragons = new HashSet()
    dragons.add "Lambton"
    dragons << "Deerhurst"
    println "2 == ${dragons.size()}"
    dragons -= ("Lambton")
    println "1 == ${dragons.size()}"
    println(dragons)
}
println "SET2"
SET2: {
    def dragons = new TreeSet()
    dragons.add "Lambton"
    dragons.add "Smaug"
    dragons.add "Deerhurst"
    dragons.add "Norbert"
    dragons += "Balaur"
    dragons << "Feilong"
    //TODO Subtract something from dragons
    println(dragons)
}

