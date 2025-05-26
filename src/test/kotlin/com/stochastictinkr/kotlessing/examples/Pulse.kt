package com.stochastictinkr.kotlessing.examples

import kotlessing.*
import kotlin.math.*

private const val period = 2000f

fun main() = runSketch {
    init {
        name("Pulse")
        size(800, 600)
        background(0f, 0f, 0f)
        frameRate(60)
    }

    fun pulse(millis: Float): Float {

        // Wrap the time into the current beat window.
        val t = (millis % period + period) % period

        // Helper producing a Gaussian bump.
        fun gauss(center: Float, width: Float, amp: Float): Float {
            val x = (t - center) / width
            return amp * exp(-x * x)
        }

        // Rough physiological timings (in ms) and relative amplitudes.
        val pWave = gauss(center = 100f, width = 30f, amp = 0.2f)
        val qrs   = gauss(center = 300f, width = 20f, amp = 1.0f)
        val tWave = gauss(center = 600f, width = 60f, amp = 0.35f)

        return (pWave + qrs + tWave).coerceIn(0f, 1f)
    }

    draw {
        hints {
            antialiasing()
            rendering(Quality)
        }
        val time = time.inWholeMilliseconds.toFloat()
        // Simulate a heartbeat-like pulse effect.  One large pulse, then a smaller one.

        centerAt(0f, 0f)
        color(1f, 0.5f, 0.5f, 1f) // Light red color for the pulse
        fill(Ellipse centeredAt Point(0f, 0f) withSideLength (200 + pulse(time) * 20f))
    }
}