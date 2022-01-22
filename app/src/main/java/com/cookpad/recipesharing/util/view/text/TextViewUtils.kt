package com.cookpad.recipesharing.util.view.text

import android.content.Context
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cookpad.recipesharing.R

object TextViewUtils {

    fun addReadMore(text: String, textView: TextView, context: Context) {
        val ss = SpannableString(text.substring(0, text.length / 2) + "... read more")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {

            override fun onClick(view: View) {
                addReadLess(text, textView, context)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ds.color = ContextCompat.getColor(context, R.color.black)
                } else {
                    ds.color = ContextCompat.getColor(context, R.color.black)
                }
            }
        }
        ss.setSpan(clickableSpan, ss.length - 10, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun addReadLess(text: String, textView: TextView, context: Context) {
        val ss = SpannableString("$text  read less")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                addReadMore(text, textView, context)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ds.color = ContextCompat.getColor(
                        context,
                        R.color.black
                    )
                } else {
                    ds.color = ContextCompat.getColor(
                        context,
                        R.color.black
                    )
                }
            }
        }
        ss.setSpan(clickableSpan, ss.length - 10, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}
