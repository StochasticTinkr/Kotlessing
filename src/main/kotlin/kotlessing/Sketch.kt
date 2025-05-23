package kotlessing

interface Sketch {
    fun InitContext.init()
    fun UpdateContext.update()
    fun DrawContext.draw()
    fun AfterDrawContext.afterDraw()

    companion object {
        inline operator fun invoke(block: SketchBuilder.() -> Unit): Sketch {
            var initBlock: InitContext.() -> Unit = {}
            var updateBlock: UpdateContext.() -> Unit = {}
            var drawBlock: DrawContext.() -> Unit = {}
            var afterDrawBlock: AfterDrawContext.() -> Unit = {}

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

                override fun afterDraw(block: AfterDrawContext.() -> Unit) {
                    afterDrawBlock = block
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

                override fun AfterDrawContext.afterDraw() {
                    afterDrawBlock()
                }
            }
        }
    }
}
