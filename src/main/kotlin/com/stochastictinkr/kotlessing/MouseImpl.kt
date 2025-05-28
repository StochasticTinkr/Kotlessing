package com.stochastictinkr.kotlessing

import kotlessing.*
import java.awt.event.*

class MouseImpl() : Mouse {
    override var position: Point = Point(0f, 0f)
        private set

    private val leftImpl = MouseButtonImpl()
    private val rightImpl = MouseButtonImpl()
    private val middleImpl = MouseButtonImpl()

    override val left: MouseButton = leftImpl;
    override val right: MouseButton = rightImpl;
    override val middle: MouseButton = middleImpl;

    private val buttons = listOf(null, leftImpl, rightImpl, middleImpl)

    val adapter = object : MouseAdapter() {
        override fun mouseMoved(e: MouseEvent) {
            position = e.position()
        }

        override fun mouseDragged(e: MouseEvent) {
            position = e.position()
        }

        override fun mousePressed(e: MouseEvent) {
            e.button().isPressed = true
        }

        override fun mouseReleased(e: MouseEvent) {
            e.button().isPressed = false
        }
    }

    private fun MouseEvent.button(): MouseButtonImpl = buttons.getOrNull(button) ?: MouseButtonImpl()
    private fun MouseEvent.position(): Point = Point(this.x.toFloat(), this.y.toFloat())
}

private class MouseButtonImpl() : MouseButton {
    override var isPressed: Boolean = false
}
