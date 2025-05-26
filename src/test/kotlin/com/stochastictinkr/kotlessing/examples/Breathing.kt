package com.stochastictinkr.kotlessing.examples

import kotlessing.*
import kotlin.math.*


fun main() = runSketch {
// Animation parameters
    val basePinch = 0.01f   // average inward bow
    val amplitude = 0.1f   // ± variation
    val speed = 0.25f        // cycles per second
    val radius = 200f

    init {
        name("Breathing")
        size(800, 600)
        background(0f, 0f, 0f)
        frameRate(60)
    }

    draw {
        hints {
            antialiasing()
            rendering(Quality)
        }
        centerAt(0f, 0f)

        val t = time.inWholeMilliseconds.toFloat() / 1000f

        val pinch = basePinch + amplitude * sin(TWO_PI * speed * t)

        val shape = Shape {
            val vx = FloatArray(6)
            val vy = FloatArray(6)
            for (i in 0 until 6) {
                val a = i * TWO_PI / 6f
                vx[i] = radius * cos(a)
                vy[i] = radius * sin(a)
            }
            moveTo(vx[0], vy[0])
            for (i in 0 until 6) {
                val j = (i + 1) % 6
                val mx = (vx[i] + vx[j]) * 0.5f
                val my = (vy[i] + vy[j]) * 0.5f
                val cx = mx * (1f - pinch)
                val cy = my * (1f - pinch)
                quadTo(cx, cy, vx[j], vy[j])
            }
            close()
        }

        color(0.8f, 0.9f, 1f)
        fill(shape)

        stroke(width = 4f)
        color(0.2f, 0.3f, 0.5f)
        draw(shape)
    }
}

private const val TWO_PI = (Math.PI * 2.0).toFloat()

