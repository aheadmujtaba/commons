package com.ahmad.commons.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlin.math.max
import kotlin.math.min

fun EditText.insertTextAtCursorLocation(text: String) {
    val start = this.selectionStart
    val end = this.selectionEnd
    this.text.replace(min(start, end), max(start, end), text, 0, text.length)
}

fun EditText.openKeyboard(){
    this.requestFocus()
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this,InputMethodManager.SHOW_IMPLICIT)
}