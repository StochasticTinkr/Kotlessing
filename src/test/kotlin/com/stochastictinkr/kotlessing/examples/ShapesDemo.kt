package com.stochastictinkr.kotlessing.examples

import kotlessing.*


fun main() {
    runSketch {
        init {
            name("Shapes")
            size(800, 600)
            background(0f, 0f, 0f)
            frameRate(0)
        }

        draw {
            hints {
                antialiasing()
                rendering(Quality)
            }
            fun drawAndFill(
                shape: Shape,
                fillColor: Color = Color(0.8f, 0.9f, 1f),
                strokeColor: Color = Color(0.2f, 0.3f, 0.5f),
            ) {
                color(fillColor)
                fill(shape)
                stroke(width = 4f)
                color(strokeColor)
                draw(shape)
            }

            val red = Color(1f, 0.2f, 0.2f)
            val darkRed = Color(0.5f, 0.1f, 0.1f)
            val green = Color(0.2f, 1f, 0.2f)
            val darkGreen = Color(0.1f, 0.5f, 0.1f)
            val blue = Color(0.2f, 0.2f, 1f)
            val darkBlue = Color(0.1f, 0.1f, 0.5f)
            val yellow = Color(1f, 1f, 0.2f)
            val darkYellow = Color(0.5f, 0.5f, 0.1f)
            val orange = Color(1f, 0.5f, 0.2f)
            val darkOrange = Color(0.5f, 0.25f, 0.1f)
            val purple = Color(0.5f, 0.2f, 1f)
            val darkPurple = Color(0.25f, 0.1f, 0.5f)

            drawAndFill(
                Rectangle centeredAt Point(100f, 100f) withSideLength 100f,
                red, darkRed
            )
            drawAndFill(
                Ellipse centeredAt Point(250f, 100f) withSideLength 100f,
                green, darkGreen
            )
            drawAndFill(
                RoundRectangle centeredAt Point(400f, 100f) withSideLength 100f withCornerRadius 10f, blue, darkBlue
            )

            drawAndFill(
                Rectangle.byCenter(100f, 300f, 100f, 100f), yellow, darkYellow
            )
            drawAndFill(
                Ellipse.byCenter(250f, 300f, 100f, 100f), orange, darkOrange
            )
            drawAndFill(
                RoundRectangle.byCenter(400f, 300f, 100f, 100f, 10f), purple, darkPurple
            )
            drawAndFill(heart(Point(600f, 300f), 100f),
                Color(1f, 0.2f, 0.6f), Color(0.5f, 0.1f, 0.3f)
            )

        }
    }
}

fun heart(center: Point, size: Float): Shape = Shape {
    val x = center.x
    val y = center.y
    val h = size                      // visual height of the heart

    // Ratios chosen to give a soft round top and a gentle point at bottom.
    val topY = y - 0.25f * h     // indentation between the lobes
    val controlUp = y - 0.75f * h     // height of the upper control points
    val controlIn = 0.5f * h          // horizontal distance of upper controls

    val bottomY = y + 0.60f * h     // tip of the heart
    val controlDn = y + 0.10f * h     // lower control points keep the belly full
    val controlOut = 1.0f * h         // horizontal distance of lower controls

    moveTo(x, topY)

    // Left half: from top centre to bottom tip.
    cubicTo(
        x - controlIn, controlUp,      // upper left control
        x - controlOut, controlDn,     // lower left control
        x, bottomY         // bottom tip
    )

    // Right half: from bottom tip back to top centre.
    cubicTo(
        x + controlOut, controlDn,     // lower right control
        x + controlIn, controlUp,     // upper right control
        x, topY           // back to the start
    )

    close()
}
