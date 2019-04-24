package com.github.adamldavis

import groovy.transform.CompileStatic
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.processors.UnicastProcessor
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import java.util.function.Supplier
import java.util.stream.Stream

/**
 * Groovy Demo of RxJava.
 */
@CompileStatic
class GroovyRxJavaDemo {

    static AtomicReference<String> observeThread = new AtomicReference('')

    static AtomicReference<String> subscribeThread = new AtomicReference('')

    static List doSquares() {
        List squares = new ArrayList()
        Flowable.range(1, 64)
                .observeOn(Schedulers.computation())
                .map{ v -> v * v }
                .doOnError{ ex -> ex.printStackTrace() }
                .doOnComplete{ println("Completed doSquares") }
                .blockingSubscribe(squares.&add)

        squares
    }

    static List doParallelSquares() {
        List squares = new ArrayList()
        Flowable.range(1, 64)
                .flatMap { v ->
                    Flowable.just(v)
                            .subscribeOn(Schedulers.computation())
                            .map { w -> w * w }
                }
                .doOnError{ ex -> ex.printStackTrace() }
                .doOnComplete{ println("Completed doParallelSquares") }
                .blockingSubscribe(squares.&add)

        squares
    }

    static void runComputation() throws Exception {
        StringBuffer sb = new StringBuffer()
        Flowable<String> source = Flowable.fromCallable {
                observeThread.set(Thread.currentThread().name)
                Thread.sleep(1000) //  imitate expensive computation
                "Done"
            }
        source.doOnComplete{ println("Completed runComputation") }

        Flowable<String> runBackground = source.subscribeOn(Schedulers.io())

        Flowable<String> showForeground = runBackground.observeOn(Schedulers.single())

        showForeground.subscribe({it ->
            subscribeThread.set(Thread.currentThread().name)
            println(it)
        }, Throwable.&printStackTrace)

    }

     static void writeFile(File file) {
        file.newPrintWriter().withCloseable { pw ->
            Flowable.range(1, 100)
                    .observeOn(Schedulers.io())
                    .blockingSubscribe(pw.&println)
        }
    }

    static void readFile(File file, Writer out) {
        file.newReader().withCloseable { br ->

            Flowable<String> flowable = Flowable.fromPublisher(new GroovyRxJavaDemo.FilePublisher(br))

            flowable.observeOn(Schedulers.io())
                    .doOnNext({ it -> out.println "thread=${Thread.currentThread().name}" } as Consumer)
                    .blockingSubscribe(out.&println)

        }
    }

    static List<String> readFileWithProcessor(File file) {
        final List<String> lines = new ArrayList<>()

        file.newReader().withCloseable { br ->

            UnicastProcessor processor = UnicastProcessor.create()
            Flowable.fromPublisher(new GroovyRxJavaDemo.FilePublisher(br))
                    .subscribeOn(Schedulers.io())
                    .subscribe(processor)
            processor.blockingSubscribe { line ->
                lines.add((String) line)
                println(line)
            }

        }
        lines
    }

    static void readFile2(File file, Writer out) {
        Single<BufferedReader> readerSingle = Single.just(file) //1
                .observeOn(Schedulers.io()) //2
                .map{ f -> f.newReader() } //3
        Flowable<String> flowable = readerSingle.flatMapPublisher{reader -> //4
                Flowable.fromIterable { //5
                    Stream.generate(readLineSupplier(reader)).iterator()
                }.takeWhile { line -> !'EOF'.equals(line) } } //6
        flowable
                .doOnNext{ it -> out.println "thread=${Thread.currentThread().name}" }
                .doOnError{ ex -> ex.printStackTrace() }
                .blockingSubscribe(out.&println) //8
    }

    static Supplier<String> readLineSupplier(BufferedReader reader) {
        return {
            try {
                String line = reader.readLine()
                return line == null ? 'EOF' : line
            } catch (IOException ex) { throw new RuntimeException(ex) }

        } as Supplier<String> // <--only required by @CompileStatic
    }

    static int countUsingBackpressure(long sleepMillis) throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0) //1
        Flowable<Long> interval =
                Observable.interval(1, TimeUnit.MILLISECONDS) //2
                        .toFlowable(BackpressureStrategy.LATEST) //3
                        .take(2000) //4
        interval.subscribe({ x->
            Thread.sleep(100) //5
            count.incrementAndGet()
        })
        Thread.sleep(sleepMillis) //6
        return count.get()
    }

    static class FilePublisher implements Publisher<String> {
        BufferedReader reader

        FilePublisher(BufferedReader reader) { this.reader = reader }

        @Override
        void subscribe(Subscriber<? super String> subscriber) {
            subscriber.onSubscribe(new GroovyRxJavaDemo.FilePublisherSubscription(this, subscriber))
        }
        String readLine() throws IOException {
            reader.readLine()
        }
    }

    static class FilePublisherSubscription implements Subscription {
        GroovyRxJavaDemo.FilePublisher publisher
        Subscriber<? super String> subscriber

        FilePublisherSubscription(GroovyRxJavaDemo.FilePublisher pub, Subscriber<? super String> sub) {
            this.publisher = pub
            this.subscriber = sub
        }
        @Override
        void request(long n) {
            try {
                String line
                for (int i = 0; i < n && publisher != null && (line = publisher.readLine()) != null; i++) {
                    if (subscriber != null) subscriber.onNext(line)
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
