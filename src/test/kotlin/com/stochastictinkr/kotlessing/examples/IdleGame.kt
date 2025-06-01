package com.stochastictinkr.kotlessing.examples

import kotlessing.*

fun main() = runSketch {
    init {
        name("Idle Game")
        size(820, 600)
        background(0f, 0f, 0f)
        frameRate(60)
    }

    class Resource(
        val color: Color,
        val left: Float,
        val right: Float,
        var count: Float = 0f,
        var rate: Float = 0f,
        var price: Float = 10f,
        var reward: Float = 0f,
    )

    val resources = listOf(
        Color(1f, 0f, 0f), // Red
        Color(1f, 0.5f, 0f), // Orange
        Color(1f, 1f, 0f), // Yellow
        Color(0f, 1f, 0f), // Green
        Color(0f, 0f, 1f), // Blue
        Color(0.5f, 0f, 1f), // Indigo
        Color(1f, 0f, 1f), // Violet
        Color(1f, 1f, 1f), // White
    ).mapIndexed { index, color ->
        Resource(
            color,
            left = index * 100f + 10f,
            right = (index + 1) * 100f,
            count = 0f,
            rate = if (index == 0) 1f else 0f, // Only the first resource generates initially
            price = index + 1f,
            reward = index * index + 1f
        )
    }

    var score = 0f

    update {
        resources.forEach { resource ->
            resource.count += resource.rate
            while (resource.count >= 100f) {
                resource.count -= 100f
                score += resource.reward
            }
        }

        mouse.left.onClick {
            if ((x % 100f) > 10f) {
                val idx = x / 100f
                resources.getOrNull(idx.toInt())?.let { resource ->
                    if (score >= resource.price) {
                        score -= resource.price
                        resource.rate += 0.1f // Increase the rate of resource generation
                        resource.price *= 1.2f // Increase the price for the next purchase
                    }
                }
            }
        }
    }

    draw {
        hints {
            antialiasing()
            rendering(Quality)
        }

        text(
            position = TextPosition(10f, 20f, inlineAnchor = InlineAnchor.Start, blockAnchor = BlockAnchor.Baseline),
            text = "Score: ${score.toInt()}"
        )

        // Draw resources
        resources.forEach { resource ->
            val shape = Rectangle.byDiagonal(
                resource.left, (1 - resource.count / 100f).coerceIn(0f, 1f) * (height - 40f),
                resource.right, height - 40f
            )
            color(resource.color)
            fill(shape)
            stroke(width = 2f)
            if (resource.price < score) {
                color(0.8f, 0.8f, 0.8f) // Lighter color for affordable resources
            } else {
                color(0.5f, 0.5f, 0.5f) // Darker color for unaffordable resources
            }
            draw(shape)

            if (resource.price < score) {
                color(1f, 1f, 1f) // White text for affordable resources
            } else {
                color(0.5f, 0.5f, 0.5f) // Gray text for unaffordable resources
            }
            text(
                position = TextPosition(
                    (resource.left + resource.right) / 2f,
                    height - 20f,
                    inlineAnchor = InlineAnchor.Center,
                    blockAnchor = BlockAnchor.Baseline
                ),
                text = "${resource.price}"
            )
        }
    }
}