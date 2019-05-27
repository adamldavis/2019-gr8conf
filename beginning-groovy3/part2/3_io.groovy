
new File("books.txt").text = "Learning Groovy\nModern Programming"

println new File("books.txt").text

new File("books.txt").eachLine { line ->
    println "line=$line"
}
/*
println "http://google.com".toURL().text
println ''

//*/
//TODO Change the above to print out your favourite website
