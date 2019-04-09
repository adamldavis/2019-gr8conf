//metaClass
String.metaClass.upper = {delegate.toUpperCase()}

println "foobar".upper()
println String.metaClass.class

interface Dog {
    void bark()
    int scratch()
}
println '--------------'
//Map
Dog dog = [bark: {println "woof!"}, scratch: { 1000 } ] as Dog

dog.bark()
println ''
println dog.scratch()

println '--------------'


//methodMissing
class Car {
    def methodMissing(String name, params) {
        def impl = { p -> println "$name $p" }
        Car.metaClass."$name" = impl
        impl(params)
    }
}
def car = new Car()
car.go("vroom", 10)
car.park()
car.switchGear("reverse")
car.findAllCarsWithModel("Honda")

car.go("clunk")

println '--------------'
//println Car.metaClass.methods
//*/

