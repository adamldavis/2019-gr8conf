
// just plain Groovy

List<Integer> squares = []

[0,1,2,3,4,5]
        .findAll { it > 0 }[0..<4]
        .collect { v-> v*v }
        .each { squares.add it }

println "squares=$squares"
