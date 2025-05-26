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

    fun byCenter(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
    ) = centeredAt(x, y).withSize(width, height)

    fun squaredByCenter(
        x: Float,
        y: Float,
        sideLength: Float,
    ) = centeredAt(x, y).withSideLength(sideLength)

    fun byCenter(
        point: Point,
        size: Size,
    ) = centeredAt(point).withSize(size)

    fun byDiagonal(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
    ) = from(x1, y1).diagonallyTo(x2, y2)

    fun byDiagonal(
        point1: Point,
        point2: Point,
    ) = from(point1).diagonallyTo(point2)

    fun byCorner(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
    ) = from(x, y).withSize(width, height)

    fun byCorner(
        point: Point,
        size: Size,
    ) = from(point).withSize(size)

    fun squaredByCorner(
        x: Float,
        y: Float,
        sideLength: Float,
    ) = from(x, y).withSideLength(sideLength)

    fun squaredByCorner(
        point: Point,
        sideLength: Float,
    ) = from(point).withSideLength(sideLength)
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

    // Same methods as the parent class, but with the corner radius parameters:

    fun byCenter(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        arcWidth: Float,
        arcHeight: Float,
    ) = centeredAt(x, y).withSize(width, height).withCornerRadius(arcWidth, arcHeight)

    fun byCenter(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        cornerRadius: Float,
    ) = centeredAt(x, y).withSize(width, height).withCornerRadius(cornerRadius)

    fun squaredByCenter(
        x: Float,
        y: Float,
        sideLength: Float,
        arcWidth: Float,
        arcHeight: Float,
    ) = centeredAt(x, y).withSideLength(sideLength).withCornerRadius(arcWidth, arcHeight)

    fun squaredByCenter(
        x: Float,
        y: Float,
        sideLength: Float,
        cornerRadius: Float,
    ) = centeredAt(x, y).withSideLength(sideLength).withCornerRadius(cornerRadius)

    fun byCenter(
        point: Point,
        size: Size,
        arcWidth: Float,
        arcHeight: Float,
    ) = centeredAt(point).withSize(size).withCornerRadius(arcWidth, arcHeight)

    fun byCenter(
        point: Point,
        size: Size,
        cornerRadius: Float,
    ) = centeredAt(point).withSize(size).withCornerRadius(cornerRadius)

    fun byDiagonal(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        arcWidth: Float,
        arcHeight: Float,
    ) = from(x1, y1).diagonallyTo(x2, y2).withCornerRadius(arcWidth, arcHeight)

    fun byDiagonal(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        cornerRadius: Float,
    ) = from(x1, y1).diagonallyTo(x2, y2).withCornerRadius(cornerRadius)

    fun byDiagonal(
        point1: Point,
        point2: Point,
        arcWidth: Float,
        arcHeight: Float,
    ) = from(point1).diagonallyTo(point2).withCornerRadius(arcWidth, arcHeight)

    fun byDiagonal(
        point1: Point,
        point2: Point,
        cornerSize: Size,
    ) = from(point1).diagonallyTo(point2).withCornerRadius(cornerSize)

    fun byDiagonal(
        point1: Point,
        point2: Point,
        cornerRadius: Float,
    ) = from(point1).diagonallyTo(point2).withCornerRadius(cornerRadius)

    fun byCorner(
        x: Float,
        y: Float,
        width: Float,
        height: Float,
        arcWidth: Float,
        arcHeight: Float,
    ) = from(x, y).withSize(width, height).withCornerRadius(arcWidth, arcHeight)

    fun byCorner(
        point: Point,
        size: Size,
        arcWidth: Float,
        arcHeight: Float,
    ) = from(point).withSize(size).withCornerRadius(arcWidth, arcHeight)

    fun byCorner(
        point: Point,
        size: Size,
        cornerRadius: Float,
    ) = from(point).withSize(size).withCornerRadius(cornerRadius)

    fun byCorner(
        point: Point,
        size: Size,
        cornerSize: Size,
    ) = from(point).withSize(size).withCornerRadius(cornerSize)

    fun squaredByCorner(
        x: Float,
        y: Float,
        sideLength: Float,
        arcWidth: Float,
        arcHeight: Float,
    ) = from(x, y).withSideLength(sideLength).withCornerRadius(arcWidth, arcHeight)

    fun squaredByCorner(
        point: Point,
        sideLength: Float,
        cornerRadius: Float,
    ) = from(point).withSideLength(sideLength).withCornerRadius(cornerRadius)

    fun squaredByCorner(
        point: Point,
        sideLength: Float,
        cornerSize: Size,
    ) = from(point).withSideLength(sideLength).withCornerRadius(cornerSize)
}
