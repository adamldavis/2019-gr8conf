package com.github.adamldavis

import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxProcessor
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.atomic.AtomicLong
import java.util.function.BiConsumer

/**
 * Groovy demo using Spring Reactor.
 */
class GroovyReactorDemo implements ReactiveStreamsDemo {


    static Flux<Long> exampleSquaresUsingGenerate() {
        Flux<Long> squares = Flux.generate(
                {new AtomicLong()}, //1
                {state, sink ->
                    long i = state.getAndIncrement()
                    sink.next(i * i) //2
                    if (i == 10) sink.complete() //3
                    return state
                })
        squares
    }

    static List<Integer> doSquares() {
        List<Integer> squares = new ArrayList<>()
        Flux.range(1, 64) // 1
                .onBackpressureBuffer(256).map{v -> v * v} // 3
                .subscribeOn(Schedulers.immediate())
                .subscribe(squares.&add) // 4

        squares
    }

    static List<Integer> doParallelSquares() {
        Flux.range(1, 64).flatMap{ v -> // 1
                Mono.just(v).subscribeOn(Schedulers.parallel()).map{w -> w * w} }
                .doOnError{ex -> ex.printStackTrace()} // 2
                .doOnComplete{ System.out.println("Completed") } // 3
                .subscribeOn(Schedulers.immediate()).collectList().block()
    }

    @Override
    Future<List<Integer>> doSquaresAsync(int count) {
        Flux.range(1, count).map{w -> w * w}
                .subscribeOn(Schedulers.immediate()).collectList().toFuture()
    }

    @Override
    Future<String> doStringConcatAsync(int count) {
        Flux.range(0, count)
                .map{i -> "i=" + i}
                .collect { new StringBuilder() }
                        {stringBuilder, o -> stringBuilder.append(o)}
                .map(StringBuilder.&toString)
                .toFuture()
    }

    @Override
    Future<List<Integer>> doParallelSquaresAsync(int count) {
        Flux.range(1, count).flatMap {
            v -> Mono.just(v).subscribeOn(Schedulers.parallel()).map{w -> w * w}
        }
                .subscribeOn(Schedulers.immediate())
                .collectList().toFuture()
    }

    @Override
    Future<String> doParallelStringConcatAsync(int count) {
        BiConsumer<StringBuilder, Object> collector =
                { stringBuilder, o -> stringBuilder.append(o) }
        Flux.range(0, count)
                .map{i -> "i=" + i}
                .window(10)
                .flatMap{ flux -> flux.subscribeOn(Schedulers.parallel())
                        .collect({ -> new StringBuilder()}, collector) }
                .collect({ -> new StringBuilder()}, collector)
                .map(StringBuilder.&toString)
                .single().toFuture()
    }

    static void runComputation() throws Exception {
        StringBuffer sb = new StringBuffer()
        Mono<String> source = Mono.fromCallable{ ->  // 1
            Thread.sleep(1000) // imitate expensive computation
            "Done"
        }
        source.doOnNext{ x -> System.out.println("Completed runComputation") }

        Flux<String> background = source.subscribeOn(Schedulers.elastic()).flux() // 2

        Flux<String> foreground = background.publishOn(Schedulers.immediate())

        foreground.subscribe(System.out.&println, Throwable.&printStackTrace)// 4
    }

    static void writeFile(File file) {
        file.withWriter { pw ->
            Flux.range(1, 100)
                    .publishOn(Schedulers
                            .fromExecutor(Executors.newFixedThreadPool(1)))
                    .subscribeOn(Schedulers.immediate()).subscribe(pw.&println)
        }
    }

    static void readFile(File file) {
        file.withReader { br ->

            Flux<String> flow = Flux.from(new ReactorDemo.FilePublisher(br))

            flow.publishOn(Schedulers.elastic())
                    .subscribeOn(Schedulers.immediate())
                    .subscribe(System.out.&println)

        }
    }

    static void useProcessor() {
        // Processor implements both Publisher and Subscriber
        // DirectProcessor is the simplest Processor from Reactor
        final FluxProcessor<String, String> processor = DirectProcessor.create()
        //TODO
    }

    static class FilePublisher implements Publisher<String> {
        BufferedReader reader

        FilePublisher(BufferedReader reader) {
            this.reader = reader
        }

        @Override
        void subscribe(Subscriber<? super String> subscriber) {
            subscriber.onSubscribe(
                    new ReactorDemo.FilePublisherSubscription(this, subscriber))
        }

        String readLine() throws IOException {
            reader.readLine()
        }
    }

    static class FilePublisherSubscription implements Subscription {
        ReactorDemo.FilePublisher publisher
        Subscriber<? super String> subscriber

        FilePublisherSubscription(ReactorDemo.FilePublisher publisher,
                                         Subscriber<? super String> subscriber) {
            this.publisher = publisher
            this.subscriber = subscriber
        }

        @Override
        void request(long n) {
            try {
                String line
                for (int i = 0; i < n && publisher != null
                        && (line = publisher.readLine()) != null; i++) {
                    if (subscriber != null)
                        subscriber.onNext(line)
                }
            } catch (IOException ex) {
                subscriber.onError(ex)
            }
            subscriber.onComplete()
        }

        @Override
        void cancel() {
            publisher = null
        }
    }
}
