package kotlessing

import java.awt.geom.*

sealed class WindingRule(internal val awtRule: Int)

data object EvenOdd : WindingRule(PathIterator.WIND_EVEN_ODD)
data object NonZero : WindingRule(PathIterator.WIND_NON_ZERO)