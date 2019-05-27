
BLOCK: { // this is how you define a block of code.
    // this is so I can reuse variable names.
}
MATH: {

    def pi = 3.141592
    
    println pi
    
    println pi.class
    
    def pie = 3.141592d
    
    println pie
    println pie.class
    
    println (pi + pie)
    println('BigD + double is ' + (pi + pie).class)
    
    println "--------------------"
    println (1f + 2d).class
    //TODO Fix the above so it does not throw a NullPointerException
}

TIMES: {
    /*3.times*/ { println "everything is an object" }
    //TODO Uncomment the above line
}


