
@Grab('com.typesafe.akka:akka-stream_2.12:2.5.12')

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.Materializer
import akka.stream.javadsl.Source

final ActorSystem system = ActorSystem.create("squares") // 1
final Materializer mat = ActorMaterializer.create(system) // 2

List<Integer> squares = new ArrayList<>()

Source.from([1,2,3])
        .map {v -> v * v}
        .runForeach(squares.&add, mat)

Thread.sleep(300)

println "squares=$squares"

System.exit(1)
