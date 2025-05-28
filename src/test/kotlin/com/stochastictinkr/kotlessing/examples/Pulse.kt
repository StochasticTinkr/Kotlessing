package com.stochastictinkr.kotlessing.examples

import kotlessing.*
import kotlin.math.*

private const val period = 1500f

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

        return (p + q + r + s + tWave)
    }

    draw {
        hints {
            antialiasing()
            rendering(Quality)
        }
        centerAt(0f, 0f)

        // --- ECG waveform effect ---
        val ecgWidth = width * .9f
        val ecgHeight = 320f
        val ecgY = 150f
        val ecgPoints = (width).toInt()


        // --- Pulsing ellipse ---
        color(0.8f, 0.45f, 0.45f)
        val pulse = pulse(time.inWholeMilliseconds.toFloat())
        val shape = Ellipse centeredAt Point(0f, 0f) withSideLength (200 + pulse * 60f)
        fill(shape)
        stroke(width = 4f)
        color(0.5f, 0.2f, 0.3f)
        draw(shape)

        val timeOffset = time.inWholeMilliseconds.toFloat()

        val ecg = Shape {
            var first = true
            for (i in 0..ecgPoints) {
                val x = -ecgWidth / 2 + i * (ecgWidth / ecgPoints)
                // Scroll the ECG waveform to the left over time
                val t = timeOffset - (ecgPoints - i) * (period / ecgPoints * 2f)
                val y = ecgY - pulse(t) * ecgHeight / 2
                if (first) {
                    moveTo(x, y)
                    first = false
                } else {
                    lineTo(x, y)
                }
            }
        }

        color(0.0f, 0.0f, 0.0f, 0.5f)
        stroke(width = 10f, join = Bevel)
        draw(ecg)

        color(0.0f, 0.8f, 0.2f, 0.5f)
        stroke(width = 3f, join = Bevel)
        draw(ecg)

        // Draw a small circle at the peak of the ECG waveform
        color(0.0f, 0.8f, 0.2f, 0.5f)
        val peakX = -ecgWidth / 2 + (ecgPoints - 1) * (ecgWidth / ecgPoints)
        val peakY = ecgY - pulse(timeOffset) * ecgHeight / 2
        fill(Ellipse centeredAt Point(peakX, peakY) withSideLength 10f)

    }
}