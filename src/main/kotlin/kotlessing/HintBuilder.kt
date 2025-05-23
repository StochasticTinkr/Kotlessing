package kotlessing

@SketchDsl
interface HintBuilder {
    fun antialiasing(enabled: Boolean = true)
    fun rendering(rendering: Rendering)
}

sealed interface Rendering
data object Speed : Rendering
data object Balanced : Rendering
data object Quality : Rendering
