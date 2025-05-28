package kotlessing

interface Mouse {
    val position: Point
    val x: Float get() = position.x
    val y: Float get() = position.y

    val left: MouseButton
    val right: MouseButton
    val middle: MouseButton
}

interface MouseButton {
    val isPressed: Boolean
}
