
@Grab('com.typesafe.akka:akka-stream_2.12:2.5.12')

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.Materializer
import akka.stream.javadsl.Source

import java.util.concurrent.CompletableFuture

final ActorSystem system = ActorSystem.create("squares") // 1
final Materializer mat = ActorMaterializer.create(system) // 2

List<Integer> squares = new ArrayList<>()

Source.from([0,1,2,3,4,5])
        .filter { it > 0 }
        .take(4)
        .map { v-> v*v }
        //.mapAsync(4) {v -> CompletableFuture.supplyAsync{ v*v }}
        .runForeach(squares.&add, mat)

Thread.sleep(300)

println "squares=$squares"

System.exit(1)
