
@Grab('io.reactivex.rxjava2:rxjava:2.1.9')

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

String thread1 = ''
String thread2 = ''
List<Integer> squares = new ArrayList<>()

Flowable.just(0,1,2,3,4,5)
        .subscribeOn(Schedulers.single()) // sets the initial thread(s)
        .filter { thread1 = Thread.currentThread().name; it > 0 }
        .take(4)
        .observeOn(Schedulers.computation()) // sets threads downstream
        .map{ i -> i*i }
        .doOnNext { thread2 = Thread.currentThread().name }
        .subscribe {
            squares.add(it)
        }

Thread.sleep(300)

println "squares=$squares"
println "thread1=$thread1"
println "thread2=$thread2"
