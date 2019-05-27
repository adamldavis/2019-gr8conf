
@Grab('com.adamldavis:shapes:0.5.1')
@Grab('com.typesafe.akka:akka-stream_2.12:2.5.12')

import shapes.*
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.Materializer
import akka.stream.javadsl.Source

import java.util.concurrent.CompletableFuture

final ActorSystem system = ActorSystem.create("shapes-system") // 1
final Materializer mat = ActorMaterializer.create(system) // 2

List stars = []

def shapes = ShapeWithColor.randomShapes()

println "shapes = ${shapes}\n"

Source.from(shapes)
        .filter { it.shape.isStar() }
        .take(3)
        //.map { v-> v.toString() }
        .async()
        .mapAsync(4) {v -> CompletableFuture.supplyAsync{ v.toString() }}
        .runForeach(stars.&add, mat)

Thread.sleep(300)

println "stars=$stars"

System.exit(1)
