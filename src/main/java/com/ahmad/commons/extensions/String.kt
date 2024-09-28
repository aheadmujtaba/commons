package com.ahmad.commons.extensions

import android.media.MediaMetadataRetriever
import android.util.Patterns
import android.webkit.URLUtil
import com.tencent.mmkv.MMKV
import java.io.File


fun <T> String.storeWithValue(value: T): Boolean {
    val kv = MMKV.defaultMMKV()
    return when (value) {
        is Boolean -> kv.encode(this, (value as Boolean))
        is Int -> kv.encode(this, (value as Int))
        is Long -> kv.encode(this, (value as Long))
        is Float -> kv.encode(this, (value as Float))
        is Double -> kv.encode(this, (value as Double))
        is String -> kv.encode(this ,(value as? String))
        else -> {
            false
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> String.getValue(defaultValue: T): T {
    val kv = MMKV.defaultMMKV()
    return when (defaultValue) {
        is Boolean -> kv.decodeBool(this, defaultValue) as T
        is Int -> kv.decodeInt(this, defaultValue) as T
        is Long -> kv.decodeLong(this, defaultValue) as T
        is Float -> kv.decodeFloat(this, defaultValue) as T
        is Double -> kv.decodeDouble(this, defaultValue) as T
        is String -> kv.decodeString(this , defaultValue) as T
        else -> {
            defaultValue
        }
    }
}

fun String.isValidUrl(): Boolean {
    return runCatching {
        URLUtil.isValidUrl(this) && Patterns.WEB_URL.matcher(this).matches()
    }.onFailure {
        it.printStackTrace()
    }.getOrElse { false }
}

fun String.getDurationFromPath(): Int {
    try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(this)
        val d = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        var duration = 0
        if (d != null) duration = d.toInt()
        retriever.close()
        return duration
    } catch (e: Exception) {
        return 0
    }
}

fun String.getFileSizeFromPath(): String {
    val file = File(this)
    return file.strSize()
}

fun String.removeWhitespaces() = this.replace(" ", "")

fun String.asFile(): File {
    return File(this)
}