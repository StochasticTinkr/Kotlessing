package kotlessing

data class Color(
    val r: Float,
    val g: Float,
    val b: Float,
    val a: Float = 1f,
) {
    constructor(r: Int, g: Int, b: Int, a: Int = 255) : this(
        r / 255f,
        g / 255f,
        b / 255f,
        a / 255f
    )
}