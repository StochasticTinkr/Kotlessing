package com.stochastictinkr.kotlessing.examples

import kotlessing.*
import kotlin.math.*

private const val period = 1000f

fun main() = runSketch {
    init {
        name("Pulse")
        size(800, 600)
        background(0f, 0f, 0f)
        frameRate(60)
    }

    fun pulse(millis: Float): Float {

        // Fold the global time into the current beat window.
        val t = ((millis % period) + period) % period

        // Gaussian helper.
        fun gauss(center: Float, width: Float, amp: Float): Float {
            val x = (t - center) / width
            return amp * exp(-x * x)
        }

        // Rough physiological timings (in ms) and relative amplitudes.
        val p = gauss(center = 120f, width = 25f * 2, amp = 0.15f)
        val q = gauss(center = 250f, width = 15f * 2, amp = -0.15f)
        val r = gauss(center = 300f, width = 12f * 2, amp = 1.00f)
        val s = gauss(center = 340f, width = 18f * 2, amp = -0.25f)
        val tWave = gauss(center = 600f, width = 70f, amp = 0.35f)

        return (p + q + r + s + tWave).coerceIn(-1f, 1f)
    }

    draw {
        hints {
            antialiasing()
            rendering(Quality)
        }
        centerAt(0f, 0f)
        color(0.8f, 0.45f, 0.45f)
        val pulse = pulse(time.inWholeMilliseconds.toFloat())
        val shape = Ellipse centeredAt Point(0f, 0f) withSideLength (200 + pulse * 60f)
        fill(shape)
        stroke(width = 4f)
        color(0.5f, 0.2f, 0.3f)
        draw(shape)
    }
}