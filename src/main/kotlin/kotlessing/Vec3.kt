package kotlessing

data class Vec3(
    val x: Float,
    val y: Float,
    val z: Float,
) {
    override fun toString(): String = "<$x, $y, $z>"
    operator fun plus(other: Vec3): Vec3 = Vec3(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vec3): Vec3 = Vec3(x - other.x, y - other.y, z - other.z)
    operator fun times(scalar: Float): Vec3 = Vec3(x * scalar, y * scalar, z * scalar)
    operator fun div(scalar: Float): Vec3 = Vec3(x / scalar, y / scalar, z / scalar)
    operator fun unaryMinus(): Vec3 = Vec3(-x, -y, -z)

    infix fun dot(other: Vec3): Float = x * other.x + y * other.y + z * other.z

    fun unit(): Vec3 {
        val len = length
        return if (len == 0f) Vec3(0f, 0f, 0f) else this / len
    }

    val length get(): Float = this dot this
    val magnitudeSquared get(): Float = this dot this

    // Swizzles:
    val xx get() = Vec2(x, x)
    val xy get() = Vec2(x, y)
    val xz get() = Vec2(x, z)
    val yx get() = Vec2(y, x)
    val yy get() = Vec2(y, y)
    val yz get() = Vec2(y, z)
    val zx get() = Vec2(z, x)
    val zy get() = Vec2(z, y)
    val zz get() = Vec2(z, z)
    val xyz get() = Vec3(x, y, z)
    val xzy get() = Vec3(x, z, y)
    val yxz get() = Vec3(y, x, z)
    val yzx get() = Vec3(y, z, x)
    val zxy get() = Vec3(z, x, y)
    val zyx get() = Vec3(z, y, x)
    val xyy get() = Vec3(x, y, y)
    val xzz get() = Vec3(x, z, z)
    val yxx get() = Vec3(y, x, x)
    val yzz get() = Vec3(y, z, z)
    val zxx get() = Vec3(z, x, x)
    val zyy get() = Vec3(z, y, y)
    val xxx get() = Vec3(x, x, x)
    val yyy get() = Vec3(y, y, y)
    val zzz get() = Vec3(z, z, z)
}

operator fun Float.times(vec: Vec3): Vec3 = vec * this

