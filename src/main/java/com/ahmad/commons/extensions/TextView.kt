package com.ahmad.commons.extensions

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import androidx.core.text.toSpannable


fun TextView.setTextClickable(
    clickablePart: String,
    color: Int? = null,
    underline: Boolean = false,
    bold: Boolean = false,
    onClick: () -> Unit
) {
    val text = this.text
    val spannableString = text.toSpannable()
    val start = text.indexOf(clickablePart).also {
        if (it == -1) return
    }
    val end = start + clickablePart.length

    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClick.invoke()
        }
    }
    spannableString.setSpan(clickableSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.movementMethod = LinkMovementMethod()

    if (underline) {
        val underlineSpan = UnderlineSpan()
        spannableString.setSpan(underlineSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    color?.let {
        val foregroundColorSpan = ForegroundColorSpan(it)
        spannableString.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    if (bold) {
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(boldSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    this.text = spannableString

}

fun TextView.setUrls(urls: List<String>) {
    val spannableStringBuilder = SpannableStringBuilder()

    for (url in urls) {
        val spannableString = SpannableString("$url\n")

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
        }

        spannableString.setSpan(clickableSpan, 0, url.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(UnderlineSpan(), 0, url.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.append(spannableString)
    }

    this.text = spannableStringBuilder
    this.movementMethod = LinkMovementMethod.getInstance()
    this.highlightColor = Color.BLUE
}