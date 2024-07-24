package com.ahmad.commons.extensions

import android.content.Intent
import android.os.Bundle

fun Bundle.toIntentData(): Intent {
    return Intent().apply {
        putExtras(this)
    }
}