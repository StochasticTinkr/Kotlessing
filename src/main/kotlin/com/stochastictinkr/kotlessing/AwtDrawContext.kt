package com.stochastictinkr.kotlessing

import kotlessing.Bevel
import kotlessing.Butt
import kotlessing.Dash
import kotlessing.DrawContext
import kotlessing.HintBuilder
import kotlessing.Keyboard
import kotlessing.Miter
import kotlessing.Mouse
import kotlessing.Round
import kotlessing.ShapeBuilder
import kotlessing.Square
import kotlessing.StrokeCap
import kotlessing.StrokeJoin
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D

class AwtDrawContext(private val g2d: Graphics2D) : DrawContext {
    override fun hints(hints: HintBuilder.() -> Unit) {
        Graphics2dHintBuilder(g2d).hints()
    }

    override fun rotate(angle: Float, x: Float, y: Float) {
        g2d.rotate(angle.toDouble(), x.toDouble(), y.toDouble())
    }

    override fun draw(shapeBuilder: ShapeBuilder.() -> Unit) {
        g2d.draw(toShape(shapeBuilder))
    }

    override fun fill(shapeBuilder: ShapeBuilder.() -> Unit) {
        g2d.fill(toShape(shapeBuilder))
    }

    override fun stroke(
        width: Float,
        cap: StrokeCap,
        join: StrokeJoin,
        dash: Dash,
    ) {
        val cap = when (cap) {
            Square -> BasicStroke.CAP_SQUARE
            Butt -> BasicStroke.CAP_BUTT
            Round -> BasicStroke.CAP_ROUND
        }
        val (join, miterLimit) = when (join) {
            Round -> BasicStroke.JOIN_ROUND to 0f
            is Miter -> BasicStroke.JOIN_MITER to join.limit
            Bevel -> BasicStroke.JOIN_BEVEL to 0f
        }
        // TODO: Handle dash pattern
        g2d.stroke = BasicStroke(width, cap, join, miterLimit)
    }

    override fun color(r: Float, g: Float, b: Float, a: Float) {
        g2d.color = Color(r, g, b, a)
    }

    override fun color(r: Int, g: Int, b: Int, a: Int) {
        g2d.color = Color(r, g, b, a)
    }

    override val mouse: Mouse
        get() = TODO("Not yet implemented")
    override val keyboard: Keyboard
        get() = TODO("Not yet implemented")
}