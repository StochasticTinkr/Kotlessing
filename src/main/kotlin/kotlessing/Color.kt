package kotlessing

import kotlin.math.*

data class Color(
    val r: Float,
    val g: Float,
    val b: Float,
    val a: Float = 1f,
) {
    constructor(r: Int, g: Int, b: Int, a: Int = 255) : this(
        r / 255f,
        g / 255f,
        b / 255f,
        a / 255f
    )

    companion object {
        operator fun invoke(
            rgb: Vec3,
            alpha: Float = 1f,
        ) = rgb.let { (r, g, b) -> Color(r, g, b, alpha) }

        /**
         * Create a color from HSB (Hue, Saturation, Brightness) values.
         */
        fun hsb(
            /**
             * Hue value. Only the fractional part is used, so it can be any float.
             */
            hue: Float,
            /**
             * Saturation value. Should be in the range [0, 1].
             */
            saturation: Float,
            /**
             * Brightness value. Should be in the range [0, 1].
             */
            brightness: Float,
            /**
             * Alpha value. Should be in the range [0, 1].
             */
            alpha: Float = 1f,
        ): Color {
            /**
             * Normalize hue to [0, 6)
             */
            fun normalizeHue(hue: Float) = (hue % 1f + 1f) % 1f * 6f

            /**
             * Calculate the chroma fraction based on how far the hue is from the middle of its sector.
             */
            fun chromaFraction(huePrime: Float) = abs(huePrime % 2f - 1f)

            val huePrime = normalizeHue(hue)
            val middle = brightness * (1 - saturation * chromaFraction(huePrime))
            val lightest = brightness
            val darkest = brightness * (1 - saturation)

            return when (val sector = huePrime.toInt()) {
                0 -> Color(lightest, middle, darkest, alpha)
                1 -> Color(middle, lightest, darkest, alpha)
                2 -> Color(darkest, lightest, middle, alpha)
                3 -> Color(darkest, middle, lightest, alpha)
                4 -> Color(middle, darkest, lightest, alpha)
                5 -> Color(lightest, darkest, middle, alpha)
                else -> error { "Invalid hue sector: $sector" }
            }
        }
    }
}