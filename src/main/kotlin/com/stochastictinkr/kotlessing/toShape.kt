package com.stochastictinkr.kotlessing

import kotlessing.ShapeBuilder
import java.awt.Shape
import java.awt.geom.Path2D

internal inline fun toShape(builder: ShapeBuilder.() -> Unit): Shape =
    Path2D.Float()
        .also {
            object : ShapeBuilder {
                override fun moveTo(x: Float, y: Float) {
                    it.moveTo(x, y)
                }

                override fun lineTo(x: Float, y: Float) {
                    it.lineTo(x, y)
                }

                override fun close() {
                    it.closePath()
                }
            }.builder()
        }