package com.wordbaze.wordbaze

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView


class  TouchableTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :  AppCompatTextView(context, attrs, defStyle)  {

    private var roundedTextColor = "#ffffff"
    private var highlightColor = "#000000"

    fun setRoundedTextColor(roundedTextColorIn: String, highlightColorIn: String) {
        roundedTextColor = roundedTextColorIn
        highlightColor = highlightColorIn
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                Log.d("motionLog", "up")
                reformText()
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d("motionLog", "cancelled")
                reformText()
            }
        }
        return super.onTouchEvent(event)
    }

    private fun reformText()    {
        val spannableString : SpannableString = text as SpannableString
        val toRemoveSpans = spannableString.getSpans(0, text.length, ForegroundColorSpan::class.java)
        for (i in toRemoveSpans.indices) spannableString.removeSpan(toRemoveSpans[i])

        val toChangeSpans = spannableString.getSpans(0, text.length, RoundedBackgroundSpan::class.java)
        for (i in toChangeSpans) {
            val start = spannableString.getSpanStart(i)
            val end = spannableString.getSpanEnd(i)
            spannableString.removeSpan(i)
            spannableString.setSpan(RoundedBackgroundSpan(Color.parseColor(highlightColor),
                Color.parseColor(roundedTextColor), 0), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

}