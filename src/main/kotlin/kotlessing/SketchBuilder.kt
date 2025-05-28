package kotlessing

@SketchDsl
interface SketchBuilder {
    fun init(block: InitContext.() -> Unit)

    fun update(block: UpdateContext.() -> Unit)

    fun draw(block: DrawContext.() -> Unit)
}
