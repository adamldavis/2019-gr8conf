
@Grab('io.projectreactor:reactor-core:3.1.7.RELEASE')

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

String thread1 = ''
String thread2 = ''
List<Integer> squares = new ArrayList<>()

Flux.just(0,1,2,3,4,5)
        .subscribeOn(Schedulers.single())  // sets the initial thread(s)
        .filter { thread1 = Thread.currentThread().name; it > 0 }
        .take(4)
        .publishOn(Schedulers.parallel()) // sets threads downstream
        .map{ x -> x*x }
        //.doOnNext { println(Thread.currentThread().name) }
        .subscribe { it ->
            thread2 = Thread.currentThread().name
            squares.add(it)
        }

Thread.sleep(300)

println "squares=$squares"
println "thread1=$thread1"
println "thread2=$thread2"

System.exit(1)
