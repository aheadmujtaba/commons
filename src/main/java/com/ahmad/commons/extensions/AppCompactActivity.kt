package com.ahmad.commons.extensions

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.adjustViewBounds(@IdRes rootId: Int) {
    findViewById<ViewGroup>(rootId)?.let {
        ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}


fun View.makeSnackBar(
    msg: String,
    length: Int = Snackbar.LENGTH_SHORT,
    actionButton: Pair<String, () -> Unit>? = null
): Snackbar {
    return Snackbar.make(this, msg, length).apply {
        actionButton?.let { p ->
            setAction(p.first) {
                p.second.invoke()
            }
        }
    }

}