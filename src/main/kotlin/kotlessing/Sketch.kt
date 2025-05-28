package kotlessing

interface Sketch {
    fun InitContext.init()
    fun UpdateContext.update()
    fun DrawContext.draw()

    companion object {
        inline operator fun invoke(block: SketchBuilder.() -> Unit): Sketch {
            var initBlock: InitContext.() -> Unit = {}
            var updateBlock: UpdateContext.() -> Unit = {}
            var drawBlock: DrawContext.() -> Unit = {}

            val builder = object : SketchBuilder {
                override fun init(block: InitContext.() -> Unit) {
                    initBlock = block
                }

                override fun update(block: UpdateContext.() -> Unit) {
                    updateBlock = block
                }

                override fun draw(block: DrawContext.() -> Unit) {
                    drawBlock = block
                }

            }

            block(builder)
            return object : Sketch {
                override fun InitContext.init() {
                    initBlock()
                }

                override fun UpdateContext.update() {
                    updateBlock()
                }

                override fun DrawContext.draw() {
                    drawBlock()
                }
            }
        }
    }
}
