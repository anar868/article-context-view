package com.wordbaze.wordbaze

import android.graphics.Color
import android.text.Spannable
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.widget.TextView


class MovementMethodArticleView : LinkMovementMethod() {

    private var pressedTextColor: String = "FF888888"
    private var pressedRoundedTextColor: String = "FF888888"
    private var roundedTextColor: String = "FF888888"
    private var highlightColor: String = "FF888888"
    private var delay: Long = 400

    fun setRoundedTextColors(pressedRoundedTextColorIn: String,
                             roundedTextColorIn: String, highlightColorIn: String) {
        pressedRoundedTextColor = pressedRoundedTextColorIn
        roundedTextColor = roundedTextColorIn
        highlightColor = highlightColorIn
    }

    fun setPressedTextColor(color: String)   {
        pressedTextColor = color
    }

    fun setDelay(number: Long) {
        delay = number
    }

    override fun onTouchEvent(widget: TextView, buffer: Spannable, event: MotionEvent): Boolean {
        val action = event.action

        var x = event.x.toInt()
        var y = event.y.toInt()
        x -= widget.totalPaddingLeft
        y -= widget.totalPaddingTop
        x += widget.scrollX
        y += widget.scrollY

        val layout = widget.layout
        val line: Int = layout.getLineForVertical(y)
        val lineLeft: Float = layout.getLineLeft(line)
        val lineRight: Float = layout.getLineRight(line)
        val off = layout.getOffsetForHorizontal(line, x.toFloat())
        val links = buffer.getSpans(off, off, ClickableSpan::class.java)

        if (links.isNotEmpty()) {
            val link = links[0]
            val backgroundSpanText = buffer.getSpans(off, off, RoundedBackgroundSpan::class.java)
            if (action == MotionEvent.ACTION_DOWN) {
                if (x > lineRight || x >= 0 && x < lineLeft) {
                    return false
                }
                if (backgroundSpanText.isNotEmpty()) {
                    buffer.removeSpan(backgroundSpanText[0])
                    buffer.setSpan(
                        RoundedBackgroundSpan(Color.parseColor(highlightColor), Color.parseColor(pressedRoundedTextColor), 0),
                        buffer.getSpanStart(link),
                        buffer.getSpanEnd(link),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else {
                    buffer.setSpan(
                        ForegroundColorSpan(Color.parseColor(pressedTextColor)),
                        buffer.getSpanStart(link),
                        buffer.getSpanEnd(link),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            } else if (action != MotionEvent.ACTION_MOVE) {
                if (backgroundSpanText.isNotEmpty()) {
                    buffer.removeSpan(backgroundSpanText[0])
                    buffer.setSpan(
                        RoundedBackgroundSpan(Color.parseColor(highlightColor), Color.parseColor(roundedTextColor), 0),
                        buffer.getSpanStart(link),
                        buffer.getSpanEnd(link),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                } else {
                    val toRemoveSpans = buffer.getSpans(off, off, ForegroundColorSpan::class.java)
                    for (i in toRemoveSpans.indices) buffer.removeSpan(toRemoveSpans[i])
                }
            }

            if (action == MotionEvent.ACTION_UP)    {
                if (x > lineRight || x >= 0 && x < lineLeft) {
                    return true
                }
                link.onClick(widget)
            }

//            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
//                val toRemoveSpans = buffer.getSpans(0, widget.text.length, ForegroundColorSpan::class.java)
//
//                currentSpan = link
//
//                // Schedule to revert color after a delay
//                widget.postDelayed({
//                    for (i in toRemoveSpans.indices) {
//                        buffer.removeSpan(toRemoveSpans[i])
//                    }
//                }, delay)
//
//            }


        }

        return true
    }

}