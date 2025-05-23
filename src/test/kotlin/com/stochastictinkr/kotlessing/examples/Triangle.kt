package com.stochastictinkr.kotlessing.examples

import com.stochastictinkr.kotlessing.*
import kotlessing.*

private const val fullCircle = 2 * Math.PI.toFloat()

fun main() {
    runSketch {
        init {
            name("Triangle")
            size(800, 600)
            background(0f, 0f, 0f)
            frameRate(60)
        }

        var angle = 0f

        update {
            angle += 0.01f
            if (angle > fullCircle) {
                angle -= fullCircle
            }
        }

        draw {
            hints {
                antialiasing()
                rendering(Quality)
            }
            rotate(angle, 150f, 150f)
            color(0f, 0f, 1f)
            fill {
                moveTo(100f, 100f)
                lineTo(200f, 100f)
                lineTo(150f, 200f)
                close()
            }
            stroke(width = 4f, cap = Round, join = Round)
            color(.25f, .25f, .25f)
            draw {
                moveTo(100f, 100f)
                lineTo(200f, 100f)
                lineTo(150f, 200f)
                close()
            }
        }
    }
}