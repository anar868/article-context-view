package com.wordbaze.wordbaze

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.FontMetricsInt
import android.graphics.RectF
import android.text.style.LineHeightSpan
import android.text.style.ReplacementSpan

/**
 * A span to create a rounded background on a text.
 *
 * If radius is set, it generates a rounded background.
 * If radius is null, it generates a circle background.
 */
class RoundedBackgroundSpan (
    private val backgroundColor: Int,
    private val textColor: Int,
    private val padding: Int = 16,
    private var radius: Float = 15f) : ReplacementSpan(),

    LineHeightSpan {
    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: FontMetricsInt?): Int {
        return (padding + paint.measureText(text, start, end) + padding).toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        paint.color = backgroundColor

        val width = paint.measureText(text, start, end)
        val height = paint.fontMetrics.descent - paint.fontMetrics.ascent + 2 * padding

        val rect = RectF(x - 2, top.toFloat(), x + width + 2 * padding + 2, bottom.toFloat() - 8)
        canvas.drawRoundRect(rect, radius, radius, paint)

        paint.color = textColor
        if (text != null) {
            canvas.drawText(text, start, end, x + padding, y.toFloat(), paint)
        }
    }

    override fun chooseHeight(
        text: CharSequence?,
        start: Int,
        end: Int,
        spanstartv: Int,
        lineHeight: Int,
        fm: FontMetricsInt?
    ) {}
}