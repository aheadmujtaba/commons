package com.ahmad.commons.extensions

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.view.ViewGroup
import android.widget.Toast
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

fun Context.shareTextVia(txt: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, txt)
    }

    startActivity(Intent.createChooser(intent, "Share via"))
}

fun Context.copyTextToClipboard(label: String = "", text: String) {
    val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val cd = ClipData.newPlainText(label, text)
    cm.setPrimaryClip(cd)
}

fun Context.addToContact(phone: String) {
    val intent = Intent(Intent.ACTION_INSERT).apply {
        type = ContactsContract.RawContacts.CONTENT_TYPE
        putExtra(ContactsContract.Intents.Insert.PHONE, phone)
        putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, "mobile")
        putExtra(ContactsContract.Intents.Insert.PHONE_ISPRIMARY, true)
    }
    startActivity(Intent.createChooser(intent, "Add to Contacts"))
}

fun Context.shareEmailVia(email: String, subject: String, body: String) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        Toast.makeText(this, "No email app available", Toast.LENGTH_SHORT).show()
    }
}

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    intent.data = Uri.parse(url)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        activityRoot()?.makeSnackBar("Unable to find application to open link")?.show()
    }
}

fun Context.shareContactVCard(vCard: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/x-vcard"
        putExtra(Intent.EXTRA_TEXT, vCard)
    }
    startActivity(Intent.createChooser(intent, "Share Contact"))
}


