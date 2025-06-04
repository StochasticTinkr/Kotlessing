package com.stochastictinkr.kotlessing

import kotlessing.*
import java.awt.*
import javax.swing.*
import java.awt.Color as AWTColor

class SketchFrame {
    private val frame: JFrame = JFrame("Kotlessing Sketch")
    private val inputs = InputsImpl()
    private val canvas: SketchCanvas = SketchCanvas(inputs)
    var sketch: Sketch? = null
        set(value) {
            field = value
            canvas.sketch = value
        }

    init {
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        ThemeManager.addRoot(frame)
    }

    class Config {
    }

    fun show() {
        var frameRate = 0
        var settingsPanel: SettingsPanel? = null
        sketch?.run {
            object : InitContext {
                override fun name(name: String) {
                    frame.title = name
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

                override fun settingsPanel(panel: SettingsPanel) {
                    settingsPanel = panel
                }
            }.init()
        }
        frame.contentPane.removeAll()
        // if settingsPanel is not null, use JSplitPane to show it alongside the canvas
        // otherwise just add the canvas
        settingsPanel?.also {
            val splitPane = JSplitPane(JSplitPane.HORIZONTAL_SPLIT)
            splitPane.leftComponent = it.component
            splitPane.rightComponent = canvas
            frame.contentPane.add(splitPane)
            it.listener = {
                canvas.repaint()
            }
        } ?: run {
            frame.contentPane.add(canvas)
        }

        if (frameRate > 0) {
            Timer(1000 / frameRate) {
                sketch?.run {
                    AwtUpdateContext(inputs).update()
                }
                canvas.repaint()

            }.start()
        }
        frame.pack()
        frame.isVisible = true
    }
}

class AwtUpdateContext(inputs: Inputs) : UpdateContext, Inputs by inputs {
    override fun showSettings(panel: SettingsPanel) = TODO("Not yet implemented")
}

