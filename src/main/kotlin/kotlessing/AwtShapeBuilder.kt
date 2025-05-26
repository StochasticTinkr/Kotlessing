package kotlessing

import java.awt.geom.Path2D

@PublishedApi
internal class AwtShapeBuilder() : ShapeBuilder {
    val path: Path2D.Float = Path2D.Float()
    override fun moveTo(x: Float, y: Float) {
        path.moveTo(x, y)
    }

    override fun lineTo(x: Float, y: Float) {
        path.lineTo(x, y)
    }

    override fun close() {
        path.closePath()
    }

    override fun quadTo(x: Float, y: Float, controlX: Float, controlY: Float) {
        path.quadTo(controlX, controlY, x, y)
    }

    override fun cubicTo(
        x: Float,
        y: Float,
        controlX1: Float,
        controlY1: Float,
        controlX2: Float,
        controlY2: Float,
    ) {
        path.curveTo(controlX1, controlY1, controlX2, controlY2, x, y)
    }

    override fun append(other: Shape, connect: Boolean) {
        path.append(other.awtShape, connect)
    }
}