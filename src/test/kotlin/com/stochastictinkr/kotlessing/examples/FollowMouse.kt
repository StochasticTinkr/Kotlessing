package com.stochastictinkr.kotlessing.examples

import kotlessing.*
import kotlin.math.*

private const val fullCircle = 2 * Math.PI.toFloat()

fun main() {
    runSketch {
        init {
            name("Follow Mouse")
            size(800, 600)
            background(0f, 0f, 0f)
            frameRate(60)
        }

        var angle = 0f
        var color = Color(0f, 0f, 1f)

        update {
            angle += 0.10f
            if (angle > fullCircle) {
                angle -= fullCircle
            }
            mouse.left.onClick {
                color = Color(
                    (0..255).random(),
                    (0..255).random(),
                    (0..255).random()
                )
            }
        }

        draw {
            hints {
                antialiasing()
                rendering(Quality)
            }
            val equilateralTriangle = Shape {
                val sideLength = 200f
                val height = (sqrt(3f) / 2) * sideLength
                val halfBase = sideLength / 2
                moveTo(-halfBase, -height / 3)
                lineTo(halfBase, -height / 3)
                lineTo(0f, height * 2 / 3)
                close()
            }
            translate(mouse.position)
            rotate(angle)
            color(color)
            fill(equilateralTriangle)
            stroke(width = 4f, cap = Stroke.Cap.Round, join = Stroke.Join.Round)
            color(.25f, .25f, .25f)
            draw(equilateralTriangle)
        }
    }
}