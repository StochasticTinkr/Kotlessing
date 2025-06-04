package com.stochastictinkr.kotlessing.examples

import kotlessing.*

fun main() {
    runSketch {
        val showPentagon = mutableValue(true)
        val radius = mutableValue(100)
        val circleColor = mutableValue(Color(0f, 0f, 1f))
        val shapeColor = mutableValue(Color(1f, 0f, 0f))

        init {
            name("Settings Demo")
            size(800, 600)
            background(0f, 0f, 0f)
            settingsPanel {
                checkbox("Show Pentagon", showPentagon)
                slider("Circle Radius", radius, range = 10..200)
                colorPicker("Circle Color", circleColor)
                colorPicker("Shape Color", shapeColor)
            }
        }

        update {
        }

        draw {
            hints {
                antialiasing()
                rendering(Quality)
            }

            if (showPentagon.value) {
                // Draw a pentagon if the toggle is enabled
                color(shapeColor.value)
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
                color(shapeColor.value)
                fill(Rectangle centeredAt Point(400f, 375f) withSideLength 100f)
            }

            // Draw a circle with a radius based on the slider value
            color(circleColor.value)
            fill(Ellipse centeredAt Point(600f, 300f) withSideLength radius.value.toFloat())
        }
    }
}