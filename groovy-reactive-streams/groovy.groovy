// just plain Groovy

@Grab('com.adamldavis:shapes:0.2')
import shapes.*

List stars = []

def shapes = ShapeWithColor.shapes

println "shapes = ${shapes}\n"

shapes
        .findAll { it.shape == Shape.ST }[0..<3]
        .collect { v -> v.toString() }
        .each { stars.add it }

println "stars=$stars"
