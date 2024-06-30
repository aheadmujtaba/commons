package com.ahmad.commons.extensions

import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment

fun Fragment.startActivity(dest: Class<*>) {
    requireContext().startActivity(dest)
}

fun <T> Fragment.askPermission(permissionLauncher: ActivityResultLauncher<T>, permission: T) {
    requireContext().askPermission(permissionLauncher, permission)
}