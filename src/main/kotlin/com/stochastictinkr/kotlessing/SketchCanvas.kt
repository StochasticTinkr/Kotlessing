package com.stochastictinkr.kotlessing

import kotlessing.*
import java.awt.*

class SketchCanvas : Component() {
    var sketch: Sketch? = null
    override fun isOpaque(): Boolean = true

    override fun paint(g: Graphics) {
        val g2d = g as Graphics2D
        g2d.background = background
        g2d.clearRect(0, 0, width, height)
        sketch?.run {
            AwtDrawContext(g2d, width, height).draw()
        }
    }
}

