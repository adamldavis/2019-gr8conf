
name = 'gr8conf build'

task test {
    dependsOn 'clean', 'build'
    doFirst { println "doing tests" }
}

task 'build', {
    doLast { println "got here!" }
}
