package kotlessing

import java.awt.geom.*

sealed class RectangularShapeFactory<S : RectangularShape, Framed>(
    private val framed: ((S) -> Unit) -> Framed,
) {
    private fun make(config: (S) -> Unit): Framed {
        return framed(config)
    }

    fun from(x: Float, y: Float) = From(x, y)

    infix fun from(point: Point) = from(point.x, point.y)

    infix fun centeredAt(point: Point) = centeredAt(point.x, point.y)
    fun centeredAt(x: Float, y: Float) = CenteredAt(x, y)

    inner class From(val x: Float, val y: Float) {
        infix fun diagonallyTo(point: Point) = diagonallyTo(point.x, point.y)

        fun diagonallyTo(x: Float, y: Float) = make {
            it.setFrameFromDiagonal(this.x.toDouble(), this.y.toDouble(), x.toDouble(), y.toDouble())
        }

        infix fun withSize(size: Size) = withSize(size.width, size.height)

        fun withSize(width: Float, height: Float) = make {
            it.setFrame(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
        }

        infix fun withSideLength(length: Float) = withSize(length, length)
    }

    inner class CenteredAt(val x: Float, val y: Float) {
        infix fun withCorner(point: Point) = withCorner(point.x, point.y)
        fun withCorner(x: Float, y: Float) = make {
            it.setFrameFromCenter(this.x.toDouble(), this.y.toDouble(), x.toDouble(), y.toDouble())
        }

        infix fun withSize(size: Size) = withSize(size.width, size.height)
        fun withSize(width: Float, height: Float) = withCorner(
            x + width * 0.5f,
            y + height * 0.5f,
        )

        infix fun withSideLength(length: Float) = withSize(length, length)
    }
}

data object Rectangle : RectangularShapeFactory<Rectangle2D, Shape>({ Shape(Rectangle2D.Float().also(it)) })
data object Ellipse : RectangularShapeFactory<Ellipse2D, Shape>({ Shape(Ellipse2D.Float().also(it)) })
data object RoundRectangle : RectangularShapeFactory<RoundRectangle2D.Float, RoundRectangle.Framed>({ Framed(it) }) {
    private fun make(
        outerConfig: (RoundRectangle2D.Float) -> Unit,
        innerConfig: (RoundRectangle2D.Float) -> Unit,
    ): Shape = Shape(RoundRectangle2D.Float().also(outerConfig).also(innerConfig))

    class Framed(
        private val config: (RoundRectangle2D.Float) -> Unit = {},
    ) {
        fun withCornerRadius(arcWidth: Float, arcHeight: Float) = make(config) {
            it.arcwidth = arcWidth
            it.archeight = arcHeight
        }

        infix fun withCornerRadius(radius: Float) = withCornerRadius(radius, radius)

        infix fun withCornerRadius(size: Size) = withCornerRadius(size.width, size.height)
    }
}
