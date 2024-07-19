package com.ahmad.commons.extensions

import android.widget.EditText
import kotlin.math.max
import kotlin.math.min

fun EditText.insertTextAtCursorLocation(text: String) {
    val start = this.selectionStart
    val end = this.selectionEnd
    this.text.replace(min(start, end), max(start, end), text, 0, text.length)
}