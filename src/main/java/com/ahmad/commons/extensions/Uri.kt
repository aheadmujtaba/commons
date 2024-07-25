package com.ahmad.commons.extensions

import android.content.Context
import android.net.Uri
import java.io.File

fun Uri.filename(): String {
    return this.pathSegments.last().substringAfterLast("/")
}

fun Uri.isSaved(context: Context, isStatusData: Boolean = false): Boolean {
    return File(
        context.externalCacheDir?.path +if(isStatusData){"/Status"}else{"/WhatsClone"}   + if (this.filename().endsWith(".mp4")) {
            savedVideoDir
        } else if (this.filename().endsWith(".jpg") or this.filename().endsWith("jpeg")) {
            savedImagesDir
        } else {
            savedDocumentDir
        }, this.filename()
    ).exists()
}