package dev.brunofelix.fitactions

import androidx.compose.ui.graphics.Color
import androidx.glance.color.ColorProvider

object GlanceColors {

    private val dayTextPrimary: Color get() = Color(0xDE000000)
    private val nightTextPrimary: Color get() = Color(0xFFFFFFFF)

    val textPrimary = ColorProvider(
        day = dayTextPrimary,
        night = nightTextPrimary
    )
}