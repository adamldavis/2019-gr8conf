
@Grab('io.projectreactor:reactor-core:3.1.7.RELEASE')

import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

String thread1 = ''
List<Integer> squares = new ArrayList<>()

Flux.just(1,2,3)
    .subscribeOn(Schedulers.newParallel("comp", 4))
    .map{ x -> println(Thread.currentThread().name); x*x }
    .subscribe { it ->
        thread1 = Thread.currentThread().name
        squares.add(it)
    }

Thread.sleep(300)

println "thread1=$thread1"
println "squares=$squares"

System.exit(1)
