package dev.brunofelix.fitactions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import androidx.annotation.FontRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.TypedValueCompat.spToPx
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext

@Composable
fun GlanceText(
    text: String,
    @FontRes font: Int,
    fontSize: TextUnit,
    color: Color,
    letterSpacing: TextUnit = 0.0.sp,
    modifier: GlanceModifier = GlanceModifier
) {
    Image(
        modifier = modifier,
        provider = ImageProvider(
            LocalContext.current.textAsBitmap(
                text = text,
                fontSize = fontSize,
                color = color,
                font = font,
                letterSpacing = letterSpacing.value
            )
        ),
        contentDescription = "",
    )
}

fun Context.textAsBitmap(
    text: String,
    fontSize: TextUnit,
    color: Color,
    letterSpacing: Float = 0.0f,
    font: Int
): Bitmap {
    val paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    paint.textSize = spToPx(fontSize.value, resources.displayMetrics)
    paint.color = color.toArgb()
    paint.textAlign = Paint.Align.LEFT
    paint.letterSpacing = letterSpacing
    paint.typeface = ResourcesCompat.getFont(this, font)

    val baseline = -paint.ascent()
    val width = (paint.measureText(text)).toInt()
    val height = (baseline + paint.descent()).toInt()
    val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(image)
    canvas.drawText(text, 0f, baseline, paint)
    return image
}
