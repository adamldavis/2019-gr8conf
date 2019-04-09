
BLOCK: { // this is how you define a block of code.
    // this is so I can reuse variable names.
}
STRINGS: {
    // String vs. GStrings

    def hello = 'Hello World!'
    println(hello)
    println(hello.class)
    
    println ''
    println 'GString:'
    def g = "this is a gstring. $hello"
    println g
    println(g.class)
}
MULTILINE: {
    def hello = '''
    This is a
    multiple line
    string.
    '''
    println hello
    def x = 1
    def gstring = """
        |This is a multiline 
        |gstring $x
    """
    println gstring.stripMargin()
}

