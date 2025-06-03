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
                    val panelComponent = JPanel()
                    panelComponent.layout = GridBagLayout()
                    panelComponent.preferredSize = Dimension(200, 0)
                    panel.settings.forEachIndexed { row, setting ->
                        val label = setting.labelComponent
                        val component = setting.component
                        val constraints = GridBagConstraints().apply {
                            fill = GridBagConstraints.HORIZONTAL
                            weightx = 1.0
                            insets = Insets(5, 5, 5, 5)
                        }
                        if (label != null) {
                            constraints.gridx = 0
                            constraints.gridy = row
                            panelComponent.add(label, constraints)
                            constraints.gridx = 1
                            panelComponent.add(component, constraints)
                        } else {
                            constraints.gridx = 0
                            constraints.gridy = row
                            constraints.gridwidth = 2
                            panelComponent.add(component, constraints)
                        }
                    }
                    frame.contentPane.add(panelComponent, BorderLayout.EAST)
                    frame.pack()
                }
            }.init()
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

class AwtUpdateContext(inputs: Inputs) : UpdateContext, Inputs by inputs

