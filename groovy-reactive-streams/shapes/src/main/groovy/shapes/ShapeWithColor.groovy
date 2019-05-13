package shapes

import java.util.concurrent.ThreadLocalRandom

class ShapeWithColor {
    // you can use ANSI escape codes to use color in your output
    static RESET = "\u001B[0m"
    static RED = "\u001B[31m"
    static GREEN = "\u001B[32m"
    static YELLOW = "\u001B[33m"
    static BLUE = "\u001B[34m"
    static PURPLE = "\u001B[35m"
    static CYAN = "\u001B[36m"
    static STAR = Shape.ST
    static CIRCLE = Shape.C
    static SQR = Shape.SQ

    static shapes = [CIRCLE * RED, STAR * GREEN, SQR * PURPLE, STAR * YELLOW, STAR * BLUE, STAR * CYAN]

    static String randomColor() { "\u001B[${ThreadLocalRandom.current().nextInt(6) + 31}m" }

    static List<ShapeWithColor> randomShapes(int size=50) { (0..(size)).collect{ Shape.randomShape() * randomColor() } }

    Shape shape
    String color
    String toString() { "  ${color}${shape.str}  $RESET" }

}
