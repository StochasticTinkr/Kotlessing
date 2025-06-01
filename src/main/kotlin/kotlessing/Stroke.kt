package kotlessing

data class Stroke(
    val width: Float = 1f,
    val cap: Cap = Cap.Butt,
    val join: Join = Join.Miter(),
    val dash: Dash = Dash.Solid,
) {
    sealed interface Cap {
        data object Square : Cap
        data object Butt : Cap
        data object Round : Cap
    }

    sealed interface Join {
        data object Round : Join
        data class Miter(val limit: Float = 10f) : Join {
            init {
                require(limit >= 1f) { "Miter limit must be at least 1" }
            }
        }

        data object Bevel : Join
    }

    infix fun modify(block: StrokeBuilder.() -> Unit): Stroke {
        var width = this.width
        var cap = this.cap
        var join = this.join
        var dash = this.dash

        object : StrokeBuilder {
            override fun width(value: Float) {
                require(value > 0) { "Stroke width must be positive" }
                width = value
            }

            override fun cap(value: Cap) {
                cap = value
            }

            override fun join(value: Join) {
                join = value
            }

            override fun dash(value: Dash) {
                dash = value
            }
        }.apply(block)

        return Stroke(
            width = width,
            cap = cap,
            join = join,
            dash = dash,
        )
    }

    companion object {
        val Default = Stroke()

        operator fun invoke(block: StrokeBuilder.() -> Unit) = Default modify block
    }
}
