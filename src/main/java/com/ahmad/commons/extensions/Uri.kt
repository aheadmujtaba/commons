package com.ahmad.commons.extensions

import android.content.Context
import android.net.Uri
import java.io.File

fun Uri.filename(): String {
    return this.pathSegments.last().substringAfterLast("/")
}

fun Uri.isSaved(context: Context): Boolean {
    return File(
        context.externalCacheDir?.path + if (this.filename().endsWith(".mp4")) {
            savedVideoDir
        } else if (this.filename().endsWith(".jpg")) {
            savedImagesDir
        } else {
            savedDocumentDir
        }, this.filename()
    ).exists()
}