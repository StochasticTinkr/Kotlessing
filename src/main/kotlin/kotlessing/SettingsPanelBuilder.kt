package kotlessing

import kotlin.reflect.*

interface SettingsPanelBuilder {
    fun group(title: String = "", block: SettingsPanelBuilder.() -> Unit)
    fun checkbox(label: String, property: MutableValue<Boolean>)
    fun slider(
        label: String,
        property: MutableValue<Int>,
        range: IntRange = 0..100,
        extent: Int = 1,
    )

    fun colorPicker(
        label: String,
        property: MutableValue<Color>,
        isInline: Boolean = false,
        allowAlpha: Boolean = false,
    )
}

fun <T> mutableValue(initial: T): MutableValue<T> = object : MutableValue<T> {
    override var value: T = initial
}

interface MutableValue<T> {
    var value: T
}

fun <T> property(property: KMutableProperty<T>): MutableValue<T> = object : MutableValue<T> {
    override var value: T
        get() = property.getter.call()
        set(value) = property.setter.call(value)
}

infix fun <T> (() -> T).and(setter: (T) -> Unit): MutableValue<T> {
    val getter = this
    return object : MutableValue<T> {
        override var value: T
            get() = getter()
            set(value) = setter(value)
    }
}