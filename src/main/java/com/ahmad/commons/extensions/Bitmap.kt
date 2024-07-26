package com.ahmad.commons.extensions

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

fun Bitmap.saveToUri(context: Context, uri: Uri) =
    runCatching {
        context.contentResolver.openOutputStream(uri)?.use {
            this.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }.onFailure {
        it.printStackTrace()
    }.getOrElse {
        false
    }