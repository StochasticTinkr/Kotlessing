package com.stochastictinkr.kotlessing

import kotlessing.Balanced
import kotlessing.HintBuilder
import kotlessing.Quality
import kotlessing.Rendering
import kotlessing.Speed
import java.awt.Graphics2D
import java.awt.RenderingHints

class Graphics2dHintBuilder(private val g2d: Graphics2D) : HintBuilder {
    override fun antialiasing(enabled: Boolean) {
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            if (enabled) RenderingHints.VALUE_ANTIALIAS_ON else RenderingHints.VALUE_ANTIALIAS_OFF
        )
    }

    override fun rendering(rendering: Rendering) {
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            when (rendering) {
                Speed -> RenderingHints.VALUE_RENDER_SPEED
                Balanced -> RenderingHints.VALUE_RENDER_DEFAULT
                Quality -> RenderingHints.VALUE_RENDER_QUALITY
            }
        )
    }
}