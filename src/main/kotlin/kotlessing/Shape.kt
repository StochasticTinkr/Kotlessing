package kotlessing

import java.awt.Shape as AWTShape

@JvmInline
value class Shape @PublishedApi internal constructor(
    internal val awtShape: AWTShape,
) {
    companion object {
        operator fun invoke(awtShape: AWTShape): Shape {
            return Shape(awtShape)
        }

        inline operator fun invoke(
            windingRule: WindingRule = NonZero,
            builder: ShapeBuilder.() -> Unit,
        ): Shape {
            return Shape(AwtShapeBuilder().apply(builder).path)
        }
    }
}
