package com.stochastictinkr.kotlessing

import kotlessing.Sketch
import kotlessing.SketchBuilder
import kotlessing.SketchDsl
import java.awt.EventQueue.invokeLater

@SketchDsl
fun runSketch(builder: SketchBuilder.() -> Unit) {
    setSystemProperties(
        "apple.awt.application.name" to "Kotlessing",
        "apple.awt.application.appearance" to "system",
        "apple.laf.useScreenMenuBar" to "true"
    )
    ThemeManager.init()

    invokeLater {
        val sketchFrame = SketchFrame()
        sketchFrame.sketch = Sketch(builder)
        sketchFrame.show()
    }
}

private fun setSystemProperties(vararg pairs: Pair<String, String>) {
    pairs.forEach { (key, value) -> System.setProperty(key, value) }
}