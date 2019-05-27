
//using "in" keyword

def doSomething() { println("doSomething() called") }
def doSomething(i, type="") { println("doSomething($i, $type) called") }

FOR_LOOP: {
    for (int i = 0; i < 10; i++) { //java style
        doSomething(i, "java")
    }
    for (i in 0..<10) doSomething(i, "groovy") // groovy style
}
println()
ARRAY_LOOP: {
//    String[] strArray = {"a", "b", "c"} //java
    String[] strArray = ["a", "b", "c"] //groovy
    
    println('java style:')
    for (int i = 0; i < strArray.length; i++)
        System.out.print(strArray[i]);
    println()
    println('Groovy style:')
    for (str in strArray) print str
}
println()
IN_LIST: {
    assert 'Groovy' in ['Java', 'Groovy']
    assert 7 in 0..10
    assert 1 in [1,1,2,3,5,8]
    assert !(1 in [0,2,4])
    //TODO Create a String array and use it with "in" keyword
}

