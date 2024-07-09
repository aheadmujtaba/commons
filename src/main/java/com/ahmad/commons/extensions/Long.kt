package com.ahmad.commons.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun Long.formatToTimeDuration(): String {
    return runCatching {

        val totalSeconds = this / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%d:%02d", minutes, seconds)
        }
    }.onFailure {
        it.printStackTrace()
    }.getOrElse {
        "?:??"
    }
}

fun Long.toDate(): String {
    return SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).run {
        format(this)
    }

}
