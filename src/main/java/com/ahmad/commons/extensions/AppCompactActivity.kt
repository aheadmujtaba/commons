package com.ahmad.commons.extensions

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.adjustViewBounds(@IdRes rootId: Int) {
    findViewById<View>(rootId)?.let {
        ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

fun AppCompatActivity.openLink(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        activityRoot()?.makeSnackBar("Unable to find application to open link")?.show()
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