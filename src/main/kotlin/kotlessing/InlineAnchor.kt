package kotlessing

sealed interface InlineAnchor {
    data object Start : InlineAnchor
    data object Center : InlineAnchor
    data object End : InlineAnchor
}