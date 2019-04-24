package com.github.adamldavis

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings
import akka.stream.FlowShape
import akka.stream.Graph
import akka.stream.IOResult
import akka.stream.Materializer
import akka.stream.OverflowStrategy
import akka.stream.SinkShape
import akka.stream.javadsl.FileIO
import akka.stream.javadsl.Flow
import akka.stream.javadsl.GraphDSL
import akka.stream.javadsl.Keep
import akka.stream.javadsl.Sink
import akka.stream.javadsl.Source
import akka.util.ByteString
import groovy.transform.CompileStatic
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

import java.nio.file.Paths
import java.util.concurrent.CompletionStage
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.Future

import static java.util.Arrays.asList

/**
 * Groovy Demo of Akka-Streams.
 */
class GroovyAkkaDemo implements ReactiveStreamsDemo {

    static final Materializer materializer = createMaterializer()

    static List<Integer> doSquares() {
        final ActorSystem system = ActorSystem.create("squares") // 1
        final Materializer mat = ActorMaterializer.create(system) // 2

        List<Integer> squares = []
        Source.range(1, 64) // 1
                .map{ v -> v * v } // 2
                .runForeach(squares.&add, mat) // 3

        try {Thread.sleep(300) } catch (Exception e) {}

        squares
    }

    static List<Integer> doParallelSquares() {

        final ActorSystem system = ActorSystem.create("parallel-squares") // 1
        final Materializer mat = ActorMaterializer.create(system) // 2

        List<Integer> squares = new ArrayList<>()
        Source.range(1, 64) //2
                .mapConcat{v -> Source.single(v).map{w -> w * w} //2
                        .runFold(asList(), {nil, x -> asList(x)}, mat).toCompletableFuture().get()}
                .runForeach({x -> squares.add(x as int)}, mat) //3

        try {Thread.sleep(300) } catch (Exception e) {}

        squares
    }

    static CompletionStage<ArrayList<Integer>> parallelSquares() {
        Source.range(1, 64) //2
                .mapConcat{ v -> Source.from(asList(v)).map{w -> w * w} //2
                        .runFold(asList(), {nil, x -> asList(x)}, materializer).toCompletableFuture().get() }
                .runFold(new ArrayList<Integer>(), {list, x ->
                    list.add(x as int)
                    list
                }, materializer).toCompletableFuture()
    }

    /** Creating a materializer with more configuration. */
    static Materializer createMaterializer() {
        final ActorSystem system = ActorSystem.create("reactive-messages") // 1
        ActorMaterializerSettings settings = ActorMaterializerSettings.create(system) //2
                .withMaxFixedBufferSize(100) //3
                .withInputBuffer(8, 16) //4

        return ActorMaterializer.create(settings, system) //5
    }


    @Override
    Future<List<Integer>> doSquaresAsync(int count) {
        Source.range(1, count).map{ x -> x * x }
                .toMat(Sink.seq(), Keep.right()).run(materializer).toCompletableFuture()
    }

    @Override
    Future<String> doStringConcatAsync(int count) {
        Source.range(0, count - 1).map{x -> "i=" + x}
                .fold(new StringBuilder()) {builder, it -> builder.append(it)}
                .map(StringBuilder.&toString)
                .toMat(Sink.last(), Keep.right()).run(materializer)
                .toCompletableFuture() as Future<String>
    }

    @Override
    Future<List<Integer>> doParallelSquaresAsync(int count) {
        Source.range(1, count)
                .mapAsyncUnordered(8) { w ->
                        Source.single(w).map{x -> x * x}.runWith(Sink.last(), materializer)
                }.toMat(Sink.seq(), Keep.right()).run(materializer).toCompletableFuture()
    }

    @Override
    Future<String> doParallelStringConcatAsync(int count) {
        Source.range(0, count - 1).mapAsyncUnordered(8) { x ->
                Source.single(x).map{i -> "i=" + i}.runWith(Sink.last(), materializer)}
                .fold(new StringBuilder()) {builder, it -> builder.append(it)}
                .map(StringBuilder.&toString)
                .toMat(Sink.last(), Keep.right()).run(materializer)
                .toCompletableFuture() as Future<String>
    }

    final Collection<String> messageList = new ConcurrentLinkedDeque<>()

    /** Adds error messages to messageList. */
    void printErrors() {

        final ActorSystem system = ActorSystem.create("reactive-messages") // 1
        final Materializer mat = ActorMaterializer.create(system) // 2

        Source<String, NotUsed> messages = getMessages()
        final Source<String, NotUsed> errors = messages
                .filter{m -> m.startsWith("Error")} // 3
                .buffer(200, OverflowStrategy.fail())
                .alsoTo(Sink.foreach(messageList.&add) as Sink).map{m -> m.toString()} // 4

        errors.runWith(Sink.foreach(System.out.&println) as Sink, mat) // 5
    }

    Source<String, NotUsed> getMessages() {
        Source.fromPublisher(publisher)
    }

    Publisher<String> publisher

    void setChannel(Channel channel) {
        publisher = Flux.create{ sink ->
            sink.onRequest(channel.&poll)
                    .onCancel(channel.&cancel)
                    .onDispose(channel.&close)
            channel.register(sink.&next)
        }
    }

    Sink<String, CompletionStage<IOResult>> lineSink(String filename) {
        return Flow.of(String.class)
                .map{s -> ByteString.fromString(s.toString() + "\n")}
                .toMat(FileIO.toPath(Paths.get(filename)), Keep.right())
    }

    void saveTextFile(List<String> text) {
        Sink<String, CompletionStage<IOResult>> sink = lineSink("testfile.txt")
        Source.from(text).runWith(sink, materializer)
    }

    Graph<SinkShape<String>, NotUsed> createFileSinkGraph() {
        GraphDSL.create{ builder ->
            FlowShape<String, String> flowShape = builder
                    .add(Flow.of(String.class).async()) //1
            Sink<String, CompletionStage<IOResult>> sink = lineSink("testfile.txt") //2
            SinkShape<String> sinkShape = builder.add(sink) //3

            builder.from(flowShape.out()).to(sinkShape) //4
            return new SinkShape<>(flowShape.in()) //5
        }
    }

    void saveTextFileUsingGraph(List<String> text) {
        Sink.fromGraph(createFileSinkGraph())
                .runWith(Source.from(text), materializer)
    }
}
