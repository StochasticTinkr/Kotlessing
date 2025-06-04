package kotlessing

@SketchDsl
interface InitContext {
    fun name(name: String)
    fun size(width: Int, height: Int)
    fun background(r: Float, g: Float, b: Float)
    fun frameRate(rate: Int)

    fun settingsPanel(block: SettingsPanelBuilder.() -> Unit) {
        settingsPanel(buildSettingsPanel(block))
    }

    fun settingsPanel(panel: SettingsPanel)
}