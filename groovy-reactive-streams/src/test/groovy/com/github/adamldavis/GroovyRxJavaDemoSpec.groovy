package com.github.adamldavis

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

import static com.github.adamldavis.DemoData.squares

class GroovyRxJavaDemoSpec extends Specification {

    @Shared
    GroovyRxJavaDemo demo = new GroovyRxJavaDemo()

    
    void "test DoSquares"() {
        expect:
        squares.toArray() == GroovyRxJavaDemo.doSquares().toArray()
    }

    void "test DoParallelSquares"() {
        expect:
        List result = GroovyRxJavaDemo.doParallelSquares()
                .stream().sorted().collect(Collectors.toList())

        squares.toArray() == result.toArray()
    }

    void "test runComputation"() throws Exception {
        expect:
        GroovyRxJavaDemo.runComputation()
        Thread.sleep(2000)

        GroovyRxJavaDemo.subscribeThread.get() == 'RxSingleScheduler-1'
        GroovyRxJavaDemo.observeThread.get() == 'RxCachedThreadScheduler-1'
    }


    @Timeout(value = 1500, unit = TimeUnit.MILLISECONDS)
    void "test readFile"() {
        given:
        def out = new ByteArrayOutputStream()
        when:
        out.newWriter().withWriter { writer ->
            demo.readFile(new File("gradle.properties"), writer)
        }
        then:
        def output = new String(out.toByteArray())
        output.contains('thread=RxCachedThreadScheduler-1')
        println output
    }

    @Timeout(value = 1500, unit = TimeUnit.MILLISECONDS)
    void "test readFile2"() {
        given:
        def out = new ByteArrayOutputStream()
        when:
        out.newWriter().withWriter { writer ->
            demo.readFile2(new File("gradle.properties"), writer)
        }
        then:
        def output = new String(out.toByteArray())
        output.contains('thread=RxCachedThreadScheduler-1')
        println output
    }

    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void "test countUsingBackpressure"() throws InterruptedException {
        expect:
        10 == demo.countUsingBackpressure(1050)
    }


    void "test Subscriber"() {
        expect:
        TestSubscriber<Integer> ts =
                Flowable.range(1, 5).test()

        5 == ts.valueCount()
    }

    
    void "test SubscriberWithException"() {
        expect:
        Flowable<Integer> flowable = Flowable.create({ source ->
            source.onNext(1)
            source.onError(new RuntimeException())
        }, BackpressureStrategy.LATEST)

        TestSubscriber<Integer> ts = flowable.test()

        ts.assertSubscribed()
        ts.assertError(RuntimeException.class)
    }

    
    void "test Observer"() {
        expect:
        TestObserver<Integer> ts =
                Observable.range(1, 5).test()

        5 == ts.valueCount()
    }

    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void "test Scheduler"() {
        expect:
        def scheduler = new TestScheduler() //1
        Observable tick = Observable
                .interval(1, TimeUnit.SECONDS, scheduler) //2
        Observable observable =
                Observable.just("foo", "bar", "biz", "baz") //3
                        .zipWith(tick, { string, index -> index + "-" + string } )//4
        TestObserver testObserver = observable
                .subscribeOn(scheduler).test() //5

        scheduler.advanceTimeBy(2300, TimeUnit.MILLISECONDS)//6

        testObserver.assertNoErrors() //7
        testObserver.assertValues("0-foo", "1-bar")
        testObserver.assertNotComplete()
    }

    @Timeout(value = 4, unit = TimeUnit.SECONDS)
    def "test onErrorResumeNext"() {
        expect:
        def flowable = Flowable.create({ source ->
            source.onNext(1)
            source.onError(new RuntimeException())
        }, BackpressureStrategy.LATEST)

        // needs "as Function" due to ambiguity of which method to call
        TestSubscriber ts = flowable.onErrorResumeNext(
                {ex -> Flowable.just(1)} as Function).test()

        ts.assertSubscribed()
        ts.assertNoErrors()
    }

}
