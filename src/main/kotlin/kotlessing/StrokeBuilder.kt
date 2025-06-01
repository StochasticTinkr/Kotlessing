package kotlessing

interface StrokeBuilder {
    fun width(value: Float)
    fun cap(value: Stroke.Cap)
    fun join(value: Stroke.Join)
    fun dash(block: DashBuilder.() -> Unit) = dash(Dash(block))
    fun dash(
        vararg pattern: Float,
        phase: Float = 0f,
    ) = dash(Dash(*pattern, phase = phase))

    fun dash(dash: Dash)
    fun solidDash() = dash(Dash.Solid)

    fun squareCap() = cap(Stroke.Cap.Square)
    fun buttCap() = cap(Stroke.Cap.Butt)
    fun roundCap() = cap(Stroke.Cap.Round)

    fun roundJoin() = join(Stroke.Join.Round)
    fun miterJoin(limit: Float = 10f) = join(Stroke.Join.Miter(limit))
    fun bevelJoin() = join(Stroke.Join.Bevel)
}