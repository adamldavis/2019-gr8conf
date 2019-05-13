package shapes

import java.util.concurrent.ThreadLocalRandom

enum Shape {
    ST('★'), SQ('■'), C('●'), T('▲'), R('▬');
    String str
    Shape(str) { this.str = str }

    ShapeWithColor multiply(String color) { new ShapeWithColor(shape: this, color: color) }

    boolean isStar() { this == ST }
    boolean isSquare() { this == SQ }
    boolean isCircle() { this == C }
    boolean isTriangle() { this == T }
    boolean isRectangle() { this == R }

    static Shape randomShape() { Shape.values()[ThreadLocalRandom.current().nextInt(Shape.values().length)] }
}
