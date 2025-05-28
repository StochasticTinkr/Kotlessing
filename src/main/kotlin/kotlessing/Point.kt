package kotlessing

@JvmInline
value class Point(val vec2: Vec2) {
    constructor(x: Float, y: Float) : this(Vec2(x, y))

    val x: Float get() = vec2.x
    val y: Float get() = vec2.y
    operator fun component1(): Float = vec2.x
    operator fun component2(): Float = vec2.y
    operator fun plus(vec2: Vec2) = Point(this.vec2 + vec2)
    operator fun minus(vec2: Vec2) = Point(this.vec2 - vec2)

    operator fun unaryMinus() = Point(-this.vec2)
}