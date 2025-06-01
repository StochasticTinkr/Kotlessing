package com.stochastictinkr.kotlessing.examples

import kotlessing.*

fun main() {
    runSketch {
        init {
            name("Follow Mouse")
            size(800, 600)
            background(0f, 0f, 0f)
            frameRate(60)
        }

        val positions = ArrayDeque<Point>()

        val colors = (0..15).map { it ->
            Color.hsb(
                hue = it / 16f,
                saturation = 1f,
                brightness = 1f,
            )
        }

        var previous = Point(400f, 300f)

        fun lerp(start: Float, end: Float, t: Float): Float {
            return start + (end - start) * t
        }

        update {
            val times = 10
            val maxTrailLength = 1000
            repeat(times) {
                val t = it / times.toFloat()
                val x = lerp(previous.x, mouse.x, t)
                val y = lerp(previous.y, mouse.y, t)
                positions.addFirst(Point(x, y))
                if (positions.size > maxTrailLength) {
                    positions.removeLast()
                }
            }
            previous = mouse.position
        }

        draw {
            hints {
                antialiasing()
                rendering(Quality)
            }

            color(0.5f, 0.5f, 0.5f)
            positions.forEachIndexed { idx, it ->
                fill(Ellipse centeredAt it withSideLength 2f)
            }

            colors.forEachIndexed { index, color ->
                val positionIdx = (index * positions.size / colors.size) % positions.size
                val position = positions[positionIdx]
                color(color)
                fill(Ellipse centeredAt position withSideLength (100f - index * 5f))
            }
        }
    }
}