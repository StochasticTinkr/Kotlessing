package com.stochastictinkr.kotlessing

import kotlessing.*
import java.awt.*
import kotlin.time.*
import kotlin.time.Duration.Companion.milliseconds

class SketchCanvas(val inputs: InputsImpl) : Component() {
    private var startTime: Long = 0L

    init {
        inputs.mouseImpl.adapter.let { adapter ->
            addMouseListener(adapter)
            addMouseMotionListener(adapter)
            addMouseWheelListener(adapter)
        }
    }

    var sketch: Sketch? = null
        set(value) {
            field = value
            startTime = System.currentTimeMillis()
        }


    override fun isOpaque(): Boolean = true

    override fun paint(g: Graphics) {
        val g2d = g as Graphics2D
        g2d.background = background
        g2d.clearRect(0, 0, width, height)
        sketch?.run {
            AwtDrawContext(g2d, width.toFloat(), height.toFloat(), timeSinceStart(), inputs).draw()
        }
    }

    private fun timeSinceStart(): Duration {
        return (System.currentTimeMillis() - startTime).milliseconds
    }
}

