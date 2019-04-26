package shapes

enum Shape {
    ST('★'), SQ('■'), C('●');
    String str
    Shape(str) { this.str = str }
    ShapeWithColor multiply(String color) { new ShapeWithColor(shape: this, color: color) }
}
