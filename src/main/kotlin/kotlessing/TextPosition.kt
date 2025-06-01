package kotlessing

data class TextPosition(
    val vec2: Vec2,
    val inlineAnchor: InlineAnchor = InlineAnchor.Start,
    val blockAnchor: BlockAnchor = BlockAnchor.Baseline,
) {
    constructor(
        x: Float,
        y: Float,
        inlineAnchor: InlineAnchor = InlineAnchor.Start,
        blockAnchor: BlockAnchor = BlockAnchor.Baseline,
    ) : this(Vec2(x, y), inlineAnchor, blockAnchor)
}

