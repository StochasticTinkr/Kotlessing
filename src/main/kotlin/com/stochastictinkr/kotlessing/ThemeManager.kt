package com.stochastictinkr.kotlessing

import com.formdev.flatlaf.*
import com.formdev.flatlaf.themes.*
import com.jthemedetecor.*
import com.jthemedetecor.util.*
import java.awt.*
import java.awt.EventQueue.*
import javax.swing.*

data object ThemeManager {
    var darkLafName = System.getProperty("kotlessing.dark.laf", "auto")
        set(value) {
            field = value
            themeChanged()
        }

    var lightLafName = System.getProperty("kotlessing.light.laf", "auto")
        set(value) {
            field = value
            themeChanged()
        }
    var lafMode = when (System.getProperty("kotlessing.laf.mode", "auto")) {
        "auto" -> LafMode.AUTO
        "dark" -> LafMode.DARK
        "light" -> LafMode.LIGHT
        else -> throw IllegalArgumentException("kotlessing.laf.mode must be one of \"auto\", \"dark\", or \"light\"")
    }
        set(value) {
            field = value
            themeChanged()
        }

    private val osThemeDetector: OsThemeDetector = OsThemeDetector.getDetector()
    private val uiRoots = mutableListOf<Component>()
    private var currentLaf: String? = null
    private val listener: (Boolean) -> Unit = {
        themeChanged()
    }

    fun init() {
        osThemeDetector.registerListener(listener)
        themeChanged()
    }

    fun dispose() {
        osThemeDetector.removeListener(listener)
        uiRoots.clear()
    }

    fun addRoot(root: Component): AutoCloseable {
        invokeLater {
            uiRoots.add(root)
            SwingUtilities.updateComponentTreeUI(root)
        }
        return AutoCloseable {
            invokeLater {
                uiRoots.remove(root)
            }
        }
    }

    fun detectLookAndFeel(): String {
        val byMode = when (lafMode) {
            LafMode.AUTO -> if (osThemeDetector.isDark) darkLafName else lightLafName
            LafMode.DARK -> darkLafName
            LafMode.LIGHT -> lightLafName
        }

        return if (byMode == "auto") {
            osLookAndFeel(osThemeDetector.isDark)
        } else {
            byMode
        }
    }

    private val osLookAndFeels =
        if (OsInfo.isMacOsMojaveOrLater())
            Pair(FlatMacLightLaf::class.java.name, FlatMacDarkLaf::class.java.name)
        else
            Pair(FlatLightLaf::class.java.name, FlatDarkLaf::class.java.name)


    private fun osLookAndFeel(isDark: Boolean): String =
        osLookAndFeels.let { (light, dark) ->
            if (isDark) dark else light
        }

    private fun themeChanged() {
        invokeLater {
            val newLookAndFormat = detectLookAndFeel()
            if (newLookAndFormat != currentLaf) {
                currentLaf = newLookAndFormat
                try {
                    UIManager.setLookAndFeel(currentLaf)
                    uiRoots.forEach { SwingUtilities.updateComponentTreeUI(it) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}

enum class LafMode {
    AUTO,
    DARK,
    LIGHT
}