
@Grab('com.adamldavis:shapes:0.2')
@Grab('io.projectreactor:reactor-core:3.1.7.RELEASE')

import shapes.*
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

String thread1 = ''
String thread2 = ''
List stars = []

Flux.fromIterable(ShapeWithColor.shapes)
        .subscribeOn(Schedulers.single())  // sets the initial thread(s)
        .filter {
            thread1 = Thread.currentThread().name
            it.shape == Shape.ST
        }
        .take(3)
        .publishOn(Schedulers.parallel()) // sets threads downstream
        .map{ v -> v.toString() }
        //.doOnNext { println(Thread.currentThread().name) }
        .subscribe { it ->
            thread2 = Thread.currentThread().name
            stars.add it
        }

Thread.sleep(300)

println "stars=$stars"
println "thread1=$thread1"
println "thread2=$thread2"

System.exit(1)
