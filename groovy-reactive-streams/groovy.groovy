// just plain Groovy

@Grab('com.adamldavis:shapes:0.5.1')
import shapes.*

List stars = []

def shapes = ShapeWithColor.randomShapes()

println "shapes = ${shapes}\n"

shapes
        .findAll { it.shape.isStar() }[0..<3]
        .collect { v -> v.toString() }
        .each { stars.add it }

println "stars=$stars"
