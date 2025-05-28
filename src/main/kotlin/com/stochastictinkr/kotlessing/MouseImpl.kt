package com.stochastictinkr.kotlessing

import kotlessing.*
import java.awt.event.*

class MouseImpl() : Mouse {
    override var position: Point = Point(0f, 0f)
        private set
    override val left = MouseButtonImpl()
    override val right = MouseButtonImpl()
    override val middle = MouseButtonImpl()

    private val buttons = listOf(null, left, right, middle)

    val adapter = object : MouseAdapter() {
        override fun mouseMoved(e: MouseEvent) = setPosition(e)
        override fun mouseDragged(e: MouseEvent) = setPosition(e)
        override fun mousePressed(e: MouseEvent) {
            buttons.getOrNull(e.button)?.isPressed = true
        }

        override fun mouseReleased(e: MouseEvent) {
            buttons.getOrNull(e.button)?.isPressed = false
        }

        override fun mouseClicked(e: MouseEvent) {
            buttons.getOrNull(e.button)?.apply {
                isClicked = true
                clickedAt = e.position()
            }
        }
    }

    private fun setPosition(e: MouseEvent) {
        position = e.position()
    }

    private fun MouseEvent.position(): Point = Point(this.x.toFloat(), this.y.toFloat())
}

class MouseButtonImpl() : MouseButton {
    override var isPressed: Boolean = false
    override var isClicked: Boolean = false
        get() = field.also {
            field = false // Reset after being checked
        }
    override var clickedAt: Point? = null
}
