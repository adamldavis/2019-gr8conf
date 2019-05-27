
// Ranges
println 'each:'
    
    (1..4).each {print it} //1234  
    println '' 
    (1..<5).each {print it} //1234

println '\n\nfor in:'
    
    for (i in 1..4) print i //1234

println '\n\nsubstring with ranges'
    def text = 'Learning Groovy'
    println text[0..<5] //learn
    println text[0..<5, 8..-2] //learn groovy

println "\n\nranges with lists"
    def list = ['paul', 'trevor', 'brie', 'michael']
    println list[0..1, 3]
//TODO Change the above to print out: paul, brie, michael


