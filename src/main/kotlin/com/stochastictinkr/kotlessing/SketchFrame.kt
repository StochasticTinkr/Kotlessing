package com.stochastictinkr.kotlessing

import kotlessing.*
import java.awt.*
import java.awt.Color as AWTColor
import javax.swing.*

class SketchFrame {
    private val frame: JFrame = JFrame("Kotlessing Sketch")
    private val canvas: SketchCanvas = SketchCanvas()
    var sketch: Sketch? = null
        set(value) {
            field = value
            canvas.sketch = value
        }

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.contentPane.add(canvas)
        ThemeManager.addRoot(frame)
    }

    class Config {
    }

    fun show() {
        var frameRate = 0
        sketch?.run {
            object : InitContext {
                override fun name(name: String) {
                    frame.name = name
                }

                override fun size(width: Int, height: Int) {
                    canvas.preferredSize = Dimension(width, height)
                }

                override fun background(r: Float, g: Float, b: Float) {
                    canvas.background = AWTColor(r, g, b)
                }

                override fun frameRate(rate: Int) {
                    frameRate = rate

                }
            }.init()
        }

        if (frameRate > 0) {
            Timer(1000 / frameRate) {
                sketch?.run {
                    object : UpdateContext {
                        override val mouse: Mouse
                            get() = TODO("Not yet implemented")
                        override val keyboard: Keyboard
                            get() = TODO("Not yet implemented")
                    }.update()
                }
                canvas.repaint()

            }.start()
        }
        frame.pack()
        frame.isVisible = true
    }
}

