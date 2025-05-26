package kotlessing

@JvmInline
value class Size(val vec2: Vec2) {
    constructor(width: Float, height: Float) : this(Vec2(width, height))

    val width: Float get() = vec2.x
    val height: Float get() = vec2.y
    operator fun component1(): Float = vec2.x
    operator fun component2(): Float = vec2.y
    operator fun times(scalar: Float): Size = Size(vec2 * scalar)
    operator fun div(scalar: Float): Size = Size(vec2 / scalar)
    fun scaled(width: Float, height: Float): Size {
        return Size(vec2.x * width, vec2.y * height)
    }

    fun scaled(vec2: Vec2): Size {
        return Size(vec2.x * vec2.x, vec2.y * vec2.y)
    }
}

infix fun Float.by(other: Float): Size {
    return Size(this, other)
}

@JvmInline
value class Width(val width: Float)

@JvmInline
value class Height(val height: Float)

infix fun Width.and(height: Height): Size = Size(width, height.height)
