package com.stochastictinkr.kotlessing.examples

import kotlessing.*

fun main() {
    runSketch {
        val settings = object : SettingsPanel("My Settings") {
            val toggleSetting by toggle("Enable Feature", true)
            val sliderSetting by slider("Adjust Value", 50, 0, 100, 1)
        }

        init {
            name("Settings Demo")
            size(800, 600)
            background(0f, 0f, 0f)
            frameRate(60)
            settingsPanel(settings)
        }

        update {
        }

        draw {
            hints {
                antialiasing()
                rendering(Quality)
            }

            if (settings.toggleSetting) {
                // Draw a pentagon if the toggle is enabled
                color(1f, 0f, 0f)
                fill {
                    moveTo(400f, 300f)
                    lineTo(450f, 350f)
                    lineTo(425f, 400f)
                    lineTo(375f, 400f)
                    lineTo(350f, 350f)
                    close()
                }
            } else {
                // Draw a rectangle if the toggle is disabled
                color(0f, 1f, 0f)
                fill(Rectangle centeredAt Point(400f, 300f) withSideLength 100f)
            }

            // Draw a circle with a radius based on the slider value
            val radius = settings.sliderSetting.toFloat()
            color(0f, 0f, 1f)
            fill(Ellipse centeredAt Point(600f, 300f) withSideLength radius)
        }
    }
}