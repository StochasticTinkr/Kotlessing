package kotlessing

import javax.swing.*

fun buildSettingsPanel(
    block: SettingsPanelBuilder.() -> Unit,
): SettingsPanel {
    val panel = JPanel()
    panel.layout = BoxLayout(panel, BoxLayout.PAGE_AXIS)
    val settingsPanel = SettingsPanel(panel)
    val builder = SettingsPanelBuilderImpl(settingsPanel, panel)
    builder.block()
    return settingsPanel
}

private class SettingsPanelBuilderImpl(val settingsPanel: SettingsPanel, val panel: JPanel) : SettingsPanelBuilder {
    override fun group(title: String, block: SettingsPanelBuilder.() -> Unit) {
        val groupPanel = JPanel()
        groupPanel.layout = BoxLayout(groupPanel, BoxLayout.PAGE_AXIS)
        if (title.isNotEmpty()) {
            panel.border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title)
        }
        val builder = SettingsPanelBuilderImpl(settingsPanel, groupPanel)
        builder.block()
        panel.add(groupPanel)
    }

    override fun checkbox(label: String, property: MutableValue<Boolean>) {
        val checkbox = JCheckBox(label)
        checkbox.isSelected = property.value
        checkbox.addActionListener {
            property.value = checkbox.isSelected
            settingsPanel.listener()

        }
        panel.add(checkbox)
    }

    override fun slider(
        label: String,
        property: MutableValue<Int>,
        range: IntRange,
        extent: Int,
    ) {
        val slider = JSlider(range.first, range.last, property.value)
        slider.model.addChangeListener {
            property.value = slider.value
            settingsPanel.listener()
        }
        slider.border = BorderFactory.createTitledBorder(label)
        panel.add(slider)
    }

    override fun colorPicker(
        label: String,
        property: MutableValue<Color>,
        isInline: Boolean,
        allowAlpha: Boolean,
    ) {
        if (!isInline) {
            val colorButton = JButton(label)
            colorButton.background = property.value.toAwtColor()
            colorButton.addActionListener {
                val newColor = JColorChooser.showDialog(
                    panel,
                    label,
                    property.value.toAwtColor(),
                    allowAlpha
                )
                if (newColor != null) {
                    colorButton.background = newColor
                    property.value = Color(newColor)
                    settingsPanel.listener()
                }
            }
            panel.add(colorButton)
        } else {
            val colorPicker = JColorChooser(property.value.toAwtColor())
            if (allowAlpha) {
                colorPicker.chooserPanels.forEach { panel ->
                    panel.isColorTransparencySelectionEnabled = true
                }
            }
            colorPicker.addPropertyChangeListener("color") { event ->
                if (event.newValue is java.awt.Color) {
                    property.value = Color(event.newValue as java.awt.Color)
                    settingsPanel.listener()
                }
            }
            colorPicker.border = BorderFactory.createTitledBorder(label)
            panel.add(colorPicker)
        }
    }
}

class SettingsPanel internal constructor(
    internal val component: JComponent,
) {
    internal var listener: () -> Unit = {}
}