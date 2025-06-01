package kotlessing

@SketchDsl
interface HintBuilder {
    fun shapeAntialiasing(enabled: Boolean = true)
    fun textAntialiasing(enabled: Boolean = true)
    fun antialiasing(
        shape: Boolean = true,
        text: Boolean = true,
    ) {
        shapeAntialiasing(shape)
        textAntialiasing(text)
    }
    fun rendering(rendering: Rendering)
}

sealed interface Rendering
data object Speed : Rendering
data object Balanced : Rendering
data object Quality : Rendering
