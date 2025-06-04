package kotlessing

@SketchDsl
interface UpdateContext : Inputs {
    fun showSettings(panel: SettingsPanel)
}