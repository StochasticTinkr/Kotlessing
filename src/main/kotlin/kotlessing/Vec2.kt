package kotlessing

import kotlin.math.*

data class Vec2(val x: Float, val y: Float) {
    override fun toString(): String = "<$x, $y>"
    operator fun plus(other: Vec2): Vec2 = Vec2(x + other.x, y + other.y)
    operator fun minus(other: Vec2): Vec2 = Vec2(x - other.x, y - other.y)
    operator fun times(scalar: Float): Vec2 = Vec2(x * scalar, y * scalar)
    operator fun div(scalar: Float): Vec2 = Vec2(x / scalar, y / scalar)
    operator fun unaryMinus(): Vec2 = Vec2(-x, -y)

    infix fun dot(other: Vec2): Float = x * other.x + y * other.y

    fun unit(): Vec2 {
        val length = sqrt(x * x + y * y)
        return if (length == 0f) Vec2(0f, 0f) else div(length)
    }

    val length get(): Float = sqrt(x * x + y * y)
    val slowLength get(): Float = hypot(x, y)
    val magnitudeSquared get(): Float = x * x + y * y
}

operator fun Float.times(vec: Vec2): Vec2 = vec * this

val X = Vec2(1f, 0f)
val Y = Vec2(0f, 1f)
