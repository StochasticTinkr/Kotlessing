package kotlessing

@SketchDsl
interface ShapeBuilder {
    fun moveTo(x: Float, y: Float)
    fun lineTo(x: Float, y: Float)
    fun close()
}
