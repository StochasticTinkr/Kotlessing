package com.stochastictinkr.kotlessing

import kotlessing.*
import kotlessing.Shape
import kotlessing.Stroke
import java.awt.*
import kotlin.time.*
import java.awt.Color as AwtColor

class AwtDrawContext(
    private val g2d: Graphics2D,
    override val width: Float, override val height: Float,
    override val time: Duration,
    inputs: Inputs,
) : DrawContext, Inputs by inputs {
    override fun hints(hints: HintBuilder.() -> Unit) {
        Graphics2dHintBuilder(g2d).hints()
    }

    override fun centerAt(x: Float, y: Float) {
        translate((width / 2 - x), (height / 2 - y))
    }

    override fun rotate(angle: Float, x: Float, y: Float) {
        g2d.rotate(angle.toDouble(), x.toDouble(), y.toDouble())
    }

    override fun translate(x: Float, y: Float) {
        g2d.translate(x.toDouble(), y.toDouble())
    }

    override fun fill(shape: Shape) = g2d.fill(shape.awtShape)
    override fun draw(shape: Shape) = g2d.draw(shape.awtShape)

    override fun stroke(
        width: Float,
        cap: Stroke.Cap,
        join: Stroke.Join,
        dash: Dash,
    ) {
        val cap = when (cap) {
            Stroke.Cap.Square -> BasicStroke.CAP_SQUARE
            Stroke.Cap.Butt -> BasicStroke.CAP_BUTT
            Stroke.Cap.Round -> BasicStroke.CAP_ROUND
        }
        val (join, miterLimit) = when (join) {
            Stroke.Join.Round -> BasicStroke.JOIN_ROUND to 0f
            is Stroke.Join.Miter -> BasicStroke.JOIN_MITER to join.limit
            Stroke.Join.Bevel -> BasicStroke.JOIN_BEVEL to 0f
        }
        g2d.stroke = BasicStroke(
            width, cap, join, miterLimit,
            dash.pattern,
            dash.phase
        )
    }

    override fun color(r: Float, g: Float, b: Float, a: Float) {
        g2d.color = AwtColor(r, g, b, a)
    }

    override fun color(r: Int, g: Int, b: Int, a: Int) {
        g2d.color = AwtColor(r, g, b, a)
    }
}

