package kotlessing

import kotlin.time.Duration

@SketchDsl
interface DrawContext : Inputs {
    val time: Duration
    val width: Float
    val height: Float
    val size: Size get() = Size(width, height)

    fun centerAt(x: Float, y: Float)
    fun centerAt(around: Point) = centerAt(around.x, around.y)
    fun rotate(angle: Float, x: Float = 0f, y: Float = 0f)
    fun rotate(angle: Float, around: Point) = rotate(angle, around.x, around.y)
    fun translate(x: Float, y: Float)
    fun translate(point: Point) = translate(point.x, point.y)
    fun hints(hints: HintBuilder.() -> Unit)
    fun color(r: Int, g: Int, b: Int, a: Int = 255)
    fun color(r: Float, g: Float, b: Float, a: Float = 1f)
    fun color(color: Color) = color(color.r, color.g, color.b, color.a)

    fun stroke(
        width: Float = 1f,
        cap: Stroke.Cap = Stroke.Cap.Butt,
        join: Stroke.Join = Stroke.Join.Miter(10f),
        dash: Dash = Dash.Solid,
    )

    fun fill(shapeBuilder: ShapeBuilder.() -> Unit) =
        fill(Shape { shapeBuilder() })
    fun draw(shapeBuilder: ShapeBuilder.() -> Unit) =
        draw(Shape { shapeBuilder() })

    fun fill(shape: Shape)
    fun draw(shape: Shape)
}