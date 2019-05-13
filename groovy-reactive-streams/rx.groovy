
@Grab('com.adamldavis:shapes:0.5.1')
@Grab('io.reactivex.rxjava2:rxjava:2.1.9')

import shapes.*
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

String thread1 = ''
String thread2 = ''
List stars = []

def shapes = ShapeWithColor.randomShapes()

println "shapes = ${shapes}\n"

Flowable.fromIterable(shapes)
        .subscribeOn(Schedulers.single()) // sets the initial thread(s)
        .filter {
            thread1 = Thread.currentThread().name
            it.shape.isStar()
        }
        .take(3)
        .observeOn(Schedulers.computation()) // sets threads downstream
        .map{ v -> v.toString() }
        .doOnNext { thread2 = Thread.currentThread().name }
        .subscribe {
            stars.add it
        }

Thread.sleep(300)

println "stars=$stars"
println "thread1=$thread1"
println "thread2=$thread2"
