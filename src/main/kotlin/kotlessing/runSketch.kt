package kotlessing

import com.stochastictinkr.kotlessing.*
import java.awt.*

@SketchDsl
fun runSketch(builder: SketchBuilder.() -> Unit) {
    System.setProperty("apple.awt.application.name", "Kotlessing")
    System.setProperty("apple.awt.application.appearance", "system")
    System.setProperty("apple.laf.useScreenMenuBar", "true")
    ThemeManager.init()

    EventQueue.invokeLater {
        val sketchFrame = SketchFrame()
        sketchFrame.sketch = Sketch(builder)
        sketchFrame.show()
    }
}