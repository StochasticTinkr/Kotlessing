package kotlessing

import java.awt.*
import javax.swing.*
import kotlin.reflect.*

abstract class SettingsPanel(val name: String) {
    internal val settings: MutableList<Setting<*>> = mutableListOf()

    private fun <T> setting(setting: Setting<T>): Setting<T> {
        settings.add(setting)
        return setting
    }

    protected fun toggle(
        label: String,
        checked: Boolean = false,
    ) = setting(ToggleSetting(label, checked))

    protected fun slider(
        label: String,
        initialValue: Int = 0,
        min: Int = 0,
        max: Int = 100,
        extent: Int = 1,
    ) = setting(SliderSetting(label, initialValue, min, max, extent))
}

sealed class Setting<T> {
    internal abstract val component: Component
    open val labelComponent: Component? = null
    abstract var value: T
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}

class ToggleSetting(
    label: String,
    initialValue: Boolean = false,
) : Setting<Boolean>() {
    override val component: JCheckBox = JCheckBox().apply {
        text = label
        isSelected = initialValue
    }

    override var value: Boolean
        get() = component.isSelected
        set(value) {
            component.isSelected = value
        }
}

class SliderSetting(
    label: String,
    initialValue: Int = 0,
    private val min: Int = 0,
    private val max: Int = 100,
    private val extent: Int = 1,
) : Setting<Int>() {
    override val component: JSlider = JSlider(min, max, initialValue).apply {
        paintTicks = true
        paintLabels = true
        majorTickSpacing = (max - min) / 10
        minorTickSpacing = (max - min) / 100
        this.extent = extent
        labelTable = createStandardLabels(majorTickSpacing)
    }

    override val labelComponent = JLabel(label).apply {
        labelFor = component
    }

    override var value: Int
        get() = component.value
        set(value) {
            component.value = value.coerceIn(min, max)
        }
}

class ColorSetting(
    label: String,
    initialValue: Color = Color(0, 0, 0, 255),
) : Setting<Color>() {
    override val component: JColorChooser = JColorChooser(initialValue.toAwtColor()).apply {
        addPropertyChangeListener("color") { evt ->
            value = Color(evt.newValue as java.awt.Color)
        }
    }

    override val labelComponent = JLabel(label).apply {
        labelFor = component
    }

    override var value: Color
        get() = Color(component.color)
        set(value) {
            component.color = value.toAwtColor()
        }
}

