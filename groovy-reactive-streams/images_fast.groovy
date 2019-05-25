@Grab('io.reactivex.rxjava2:rxjava:2.1.9')

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

import javax.imageio.ImageIO
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.util.concurrent.CountDownLatch

String thread1 = ''
String thread2 = ''

def shapes = ['circle.png', 'star.png', 'rectangle.png'] * 10
long start = System.currentTimeMillis()
CountDownLatch latch = new CountDownLatch(shapes.size())

println "shapes = ${shapes}\n"

Flowable.fromIterable(shapes)
        .subscribeOn(Schedulers.io())
        .doAfterNext { thread1 = Thread.currentThread().name }
        .flatMap { path -> // map vs. flatMap
            Flowable.just(path).subscribeOn(Schedulers.io())
                .map { it -> ImageIO.read(new File(it)) }
                .map { image ->
                    doubleImage(image)
                    latch.countDown()
                    thread2 = Thread.currentThread().name
                }
        }
        .subscribe()

latch.await()
println "thread1=$thread1"
println "thread2=$thread2"
println "took=${System.currentTimeMillis() - start} ms"

def doubleImage(BufferedImage img) throws IOException {
    def newImg = new BufferedImage(1500, 1500, BufferedImage.TYPE_INT_RGB)
    def graphics2D = newImg.createGraphics()
    def at = AffineTransform.getScaleInstance(2, 2)

    graphics2D.drawRenderedImage(img, at)
    ImageIO.write(newImg, "png", File.createTempFile("image", "png"))
}
