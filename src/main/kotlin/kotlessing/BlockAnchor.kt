package kotlessing

sealed interface BlockAnchor {
    data object Start : BlockAnchor
    data object Center : BlockAnchor
    data object End : BlockAnchor
    data object Baseline : BlockAnchor
    data class BaselineOffset(val offset: Float) : BlockAnchor
}