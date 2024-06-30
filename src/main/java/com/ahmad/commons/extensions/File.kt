package com.ahmad.commons.extensions

import java.io.File

const val savedVideoDir = "/SavedVideos/"
const val savedImagesDir = "/SavedImages/"
const val savedDocumentDir = "/SavedDocuments/"
const val savedQrCodeDir = "/QrCodes/"
val File.size get() = if (!exists()) 0.0 else length().toDouble()
val File.sizeInKb get() = size / 1024
val File.sizeInMb get() = sizeInKb / 1024
val File.sizeInGb get() = sizeInMb / 1024
val File.sizeInTb get() = sizeInGb / 1024


fun File.sizeStrInKb(decimals: Int = 0): String = "%.${decimals}f".format(sizeInKb)
fun File.sizeStrInMb(decimals: Int = 0): String = "%.${decimals}f".format(sizeInMb)
fun File.sizeStrInGb(decimals: Int = 0): String = "%.${decimals}f".format(sizeInGb)


fun File.sizeStrWithKb(decimals: Int = 0): String = sizeStrInKb(decimals) + "Kb"
fun File.sizeStrWithMb(decimals: Int = 0): String = sizeStrInMb(decimals) + "Mb"
fun File.sizeStrWithGb(decimals: Int = 0): String = sizeStrInGb(decimals) + "Gb"

fun Double.formatFileSize(): String {
    val sizeBytes = this
    val KB = 1024.0
    val MB = KB * 1024
    val GB = MB * 1024

    return when {
        sizeBytes >= GB -> String.format("%.2f GB", sizeBytes / GB)
        sizeBytes >= MB -> String.format("%.2f MB", sizeBytes / MB)
        sizeBytes >= KB -> String.format("%.2f KB", sizeBytes / KB)
        else -> String.format("%.0f Bytes", sizeBytes)
    }
}

fun File.strSize(): String {
    return runCatching {
        if (this.exists()) {
            this.size.formatFileSize()
        } else {
            "0 ??"
        }
    }.onFailure {
        it.printStackTrace()
    }.getOrElse {
        "0 bytes"
    }
}
