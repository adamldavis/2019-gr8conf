
// DEFAULT VALUES FOR PARAMETERS

class SpaceShip {
    static void liftOff(String name = "SpaceShip") {
        println "$name lifting off!"
    }
}

SpaceShip.liftOff()
println '--'
SpaceShip.liftOff('Falcon Heavy')

