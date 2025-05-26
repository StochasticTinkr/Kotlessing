package kotlessing

import kotlin.time.Duration

@SketchDsl
interface DrawContext : Inputs {
    val time: Duration
    val width: Float
    val height: Float
    val size: Size get() = Size(width, height)

    fun centerAt(x: Float, y: Float)
    fun rotate(angle: Float, x: Float = 0f, y: Float = 0f)
    fun rotate(angle: Float, around: Point) = rotate(angle, around.x, around.y)
    fun hints(hints: HintBuilder.() -> Unit)
    fun color(r: Int, g: Int, b: Int, a: Int = 255)
    fun color(r: Float, g: Float, b: Float, a: Float = 1f)
    fun stroke(
        width: Float = 1f,
        cap: StrokeCap = Butt,
        join: StrokeJoin = Miter(10f),
        dash: Dash = Solid,
    )

    fun fill(shapeBuilder: ShapeBuilder.() -> Unit) =
        fill(Shape { shapeBuilder() })
    fun draw(shapeBuilder: ShapeBuilder.() -> Unit) =
        draw(Shape { shapeBuilder() })

    fun fill(shape: Shape)
    fun draw(shape: Shape)
}

sealed interface StrokeCap
sealed interface StrokeJoin

data object Square : StrokeCap
data object Butt : StrokeCap
data object Round : StrokeCap, StrokeJoin
data class Miter(
    val limit: Float = 10f,
) : StrokeJoin

data object Bevel : StrokeJoin

interface Dash

data object Solid : Dash

