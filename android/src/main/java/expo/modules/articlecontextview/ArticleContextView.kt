package expo.modules.articlecontextview

import android.content.Context
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import com.wordbaze.wordbaze.MovementMethodArticleView
import com.wordbaze.wordbaze.RoundedBackgroundSpan
import com.wordbaze.wordbaze.TouchableTextView
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.viewevent.EventDispatcher
import expo.modules.kotlin.views.ExpoView

class ArticleContextView(context: Context, appContext: AppContext) : ExpoView(context, appContext)  {
    val onWordClick by EventDispatcher()
    private val textView = TouchableTextView(context)
    private val method = MovementMethodArticleView()
    private val punctuation = listOf("," , ".", "!", "?", ";", ":", "...", '—', '–', '-')

    init {
        addView(textView)
    }

    fun updateWords(wordsArray: List<List<String>>) {
        val text = wordsArray[0]
        val highlightedWords = wordsArray[1].toMutableList()
        val highlightColor = wordsArray[2][0]
        val roundedTextColor = wordsArray[3][0]
        val pressedRoundedTextColor = wordsArray[4][0]
        method.setRoundedTextColors(pressedRoundedTextColor, roundedTextColor, highlightColor)
        textView.setRoundedTextColor(roundedTextColor, highlightColor)
        val spannableString = SpannableStringBuilder(mergeText(text))
        var start = 0
        for (word in text) {
            if (word.isNotBlank()) {
                val end = start + word.length
                if (!punctuation.contains(word)) {
                    if (Regex("\\w+").containsMatchIn(word)) {

                        val clickableSpan = object : ClickableSpan() {

                            override fun onClick(view: View) {
                                onWordClick(mapOf("word" to word))
                            }
                            override fun updateDrawState(ds: TextPaint) {
                                ds.isUnderlineText = false
                            }
                        }
                        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                        val iterator = highlightedWords.listIterator()
                        while (iterator.hasNext()) {
                            val currentString = iterator.next()
                            if (currentString.equals(word, ignoreCase = true)) {
                                spannableString.setSpan(RoundedBackgroundSpan(Color.parseColor(highlightColor),
                                Color.parseColor(roundedTextColor), 0),
                                start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                iterator.remove() 
                                break 
                            }
                        }

                    }

                    start = end + 1
                }   else {
                    start = end
                }
            }
        }        

        textView.text = spannableString
        textView.movementMethod = method
    }

    private fun mergeText(wordsArray: List<String>): String {
        var words = ""

        for (i in 0..wordsArray.lastIndex)    {
            val isLastWord = i == wordsArray.lastIndex
            val isPunctuation = punctuation.contains(wordsArray[i])
            val nextWordIsPunctuation = !isLastWord && punctuation.contains(wordsArray[i + 1])

            if (!isPunctuation) {
                words += wordsArray[i]
            }

            words += if (!nextWordIsPunctuation) {
                " "
            } else {
                wordsArray[i + 1]
            }
        }

        return words
    }

    fun setFontSize(fontSize: Float) {
        textView.textSize = fontSize
    }

    fun setColor(color: String) {
        textView.setTextColor(Color.parseColor(color))
        if (color == "#000000") {
            method.setPressedTextColor("#cccccc")
        } else {
            method.setPressedTextColor("#323232")
        }
    }

    fun setDelay(delay: Long)   {
        method.setDelay(delay)
    }

    fun setLineSpacing(lineSpacing: Float)    {
        textView.setLineSpacing(lineSpacing, 1F)
    }
}
