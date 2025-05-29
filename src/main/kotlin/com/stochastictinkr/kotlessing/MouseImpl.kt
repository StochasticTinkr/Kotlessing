package com.stochastictinkr.kotlessing

import kotlessing.*
import java.awt.event.*

class MouseImpl : Mouse {
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
            e.button().apply {
                dragEvents = e.position()
            }
        }

        override fun mousePressed(e: MouseEvent) {
            e.button().apply {
                isPressed = true
                pressEvent = e.position()
            }
        }

        override fun mouseReleased(e: MouseEvent) {
            e.button().apply {
                isPressed = false
                releaseEvent = e.position()
            }
        }

        override fun mouseClicked(e: MouseEvent) {
            e.button().apply {
                clickEvent = e.position()
            }
        }
    }

    private fun MouseEvent.button(): MouseButtonImpl = buttons.getOrNull(button) ?: MouseButtonImpl()
    private fun MouseEvent.position(): Point = Point(this.x.toFloat(), this.y.toFloat())

    // Todo: Add support to enable event queuing in the InitContext and UpdateContext.
    private inner class MouseButtonImpl : MouseButton {
        override var isPressed: Boolean = false
        var clickEvent: Point? = null
        var pressEvent: Point? = null
        var releaseEvent: Point? = null
        var dragEvents: Point? = null

        override fun onClick(action: MouseEventContext.() -> Unit) {
            clickEvent?.let {
                dispatch(action, it)
                clickEvent = null
            }
        }

        override fun onPress(action: MouseEventContext.() -> Unit) {
            pressEvent?.let {
                dispatch(action, it)
                pressEvent = null
            }
        }

        override fun onRelease(action: MouseEventContext.() -> Unit) {
            releaseEvent?.let {
                dispatch(action, it)
                releaseEvent = null
            }
        }

        override fun onDrag(action: MouseEventContext.() -> Unit) {
            dragEvents?.let {
                dispatch(action, it)
                dragEvents = null
            }
        }

        private fun dispatch(action: MouseEventContext.() -> Unit, point: Point) {
            action(MouseEventContextImpl(point, this))
        }
    }

    private class MouseEventContextImpl(
        override val position: Point,
        override val button: MouseButton,
    ) : MouseEventContext
}
