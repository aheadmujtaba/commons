package com.ahmad.commons.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

fun Context.startActivity(dest: Class<*>, clearTask: Boolean = false, flags: Int? = null) {
    runCatching {
        this.startActivity(Intent(this, dest).apply {
            if (flags != null) {
                this.flags = flags
            }
            if (clearTask) {
                this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        })
    }.onFailure {
        it.printStackTrace()
    }
}

fun Context.askNotificationPermission(permissionLauncher: ActivityResultLauncher<String>) {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R && ContextCompat.checkSelfPermission(
            this,
            NOTIFICATION_PERMISSION
        ) == PackageManager.PERMISSION_DENIED
    ) {
        permissionLauncher.launch(NOTIFICATION_PERMISSION)
    }
}

fun <T> Context.askPermission(permissionLauncher: ActivityResultLauncher<T>, d: T) {
    permissionLauncher.launch(d)
}

fun Context.activityRoot() =

    (this as? Activity)?.let {
        it.findViewById<ViewGroup>(android.R.id.content)

            ?.getChildAt(0)
    }


