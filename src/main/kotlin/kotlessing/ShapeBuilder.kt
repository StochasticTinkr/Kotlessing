package kotlessing


interface ShapeBuilder {
    fun moveTo(x: Float, y: Float)
    fun lineTo(x: Float, y: Float)
    fun quadTo(controlX: Float, controlY: Float, x: Float, y: Float)
    fun cubicTo(controlX1: Float, controlY1: Float, controlX2: Float, controlY2: Float, x: Float, y: Float)
    fun close()
    fun append(other: Shape, connect: Boolean = false)

    fun moveTo(v: Point) = moveTo(v.x, v.y)
    fun lineTo(v: Point) = lineTo(v.x, v.y)
    fun quadTo(control: Point, v: Point) = quadTo(control.x, control.y, v.x, v.y)
    fun cubicTo(control1: Point, control2: Point, v: Point) =
        cubicTo(control1.x, control1.y, control2.x, control2.y, v.x, v.y)

    fun moveTo(v: Vec2) = moveTo(v.x, v.y)
    fun lineTo(v: Vec2) = lineTo(v.x, v.y)
    fun quadTo(control: Vec2, v: Vec2) = quadTo(control.x, control.y, v.x, v.y)
    fun cubicTo(control1: Vec2, control2: Vec2, v: Vec2) =
        cubicTo(control1.x, control1.y, control2.x, control2.y, v.x, v.y)
}
