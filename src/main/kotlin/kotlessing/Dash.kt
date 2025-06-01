package kotlessing

import com.stochastictinkr.kotlessing.*

class Dash internal constructor(
    internal val pattern: FloatArray? = null,
    internal val phase: Float = 0f,
) {
    init {
        if (pattern != null) {
            require(pattern.isNotEmpty()) { "Dash pattern must not be empty" }
            require(pattern.all { it >= 0 }) { "Dash pattern values must be non-negative" }
            require(pattern.any { it > 0 }) { "Dash pattern must contain at least one non-zero value" }
            require(phase >= 0) { "Dash phase must be non-negative" }
        }
    }

    companion object {
        val Solid = Dash(pattern = null)

        operator fun invoke(
            vararg pattern: Float,
            phase: Float = 0f,
        ) = Dash(
            pattern = pattern.takeUnless { it.isEmpty() }?.copyOf(),
            phase = phase,
        )

        operator fun invoke(
            build: DashBuilder.() -> Unit,
        ): Dash {
            val pattern = FloatArrayList()
            var phase = 0f
            val builder = object : DashBuilder {
                override fun pattern(vararg values: Float) {
                    require(values.all { it >= 0 }) { "Dash pattern values must be non-negative" }
                    pattern.addAll(*values)
                }

                override fun phase(value: Float) {
                    require(value >= 0) { "Dash phase must be non-negative" }
                    phase = value
                }
            }
            build(builder)
            require(pattern.isNotEmpty) { "Dash pattern must not be empty" }
            return Dash(
                pattern = pattern.toFloatArray(),
                phase = phase,
            )
        }
    }
}