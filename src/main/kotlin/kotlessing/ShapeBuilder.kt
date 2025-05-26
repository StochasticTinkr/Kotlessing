package kotlessing


interface ShapeBuilder {
    fun moveTo(x: Float, y: Float)
    fun lineTo(x: Float, y: Float)
    fun quadTo(x: Float, y: Float, controlX: Float, controlY: Float)
    fun cubicTo(x: Float, y: Float, controlX1: Float, controlY1: Float, controlX2: Float, controlY2: Float)
    fun close()
    fun append(other: Shape, connect: Boolean = false)

    fun moveTo(v: Point) = moveTo(v.x, v.y)
    fun lineTo(v: Point) = lineTo(v.x, v.y)
    fun quadTo(v: Point, control: Point) = quadTo(v.x, v.y, control.x, control.y)
    fun cubicTo(v: Point, control1: Point, control2: Point) =
        cubicTo(v.x, v.y, control1.x, control1.y, control2.x, control2.y)

    fun moveTo(v: Vec2) = moveTo(v.x, v.y)
    fun lineTo(v: Vec2) = lineTo(v.x, v.y)
    fun quadTo(v: Vec2, control: Vec2) = quadTo(v.x, v.y, control.x, control.y)
    fun cubicTo(v: Vec2, control1: Vec2, control2: Vec2) =
        cubicTo(v.x, v.y, control1.x, control1.y, control2.x, control2.y)
}
