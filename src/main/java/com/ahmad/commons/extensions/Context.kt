package com.ahmad.commons.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.ContactsContract
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat


/**
 * -----------------------------------------------------------------------------
 *  Project: Ahmad Commons
 *  File: Context.kt
 *  Created by: Ahmad Mujtaba
 *  Date: July 24, 2024
 *  Description: This file contains some extension function of the context class
 *               that might help you.
 * -----------------------------------------------------------------------------
 *
 *  This file is part of the PDF Protection App project.
 *
 *  Author: Ahmad Mujtaba
 *  Contact: aheadMujtaba@gmail.com
 *
 *  I am not responsible or any kind damage or lost of data use at your own risk
 *  thanks for understanding.
 *
 *  -----------------------------------------------------------------------------
 *  Change History:
 *  -----------------------------------------------------------------------------
 *  Date          Author                Description
 *  -----------------------------------------------------------------------------
 *  July 24, 2024 Ahmad Mujtaba        Initial creation and addition of some functions.
 *  -----------------------------------------------------------------------------
 *
 *  License: MIT License
 *  -----------------------------------------------------------------------------
 */


fun Context.startActivity(
    dest: Class<*>,
    clearTask: Boolean = false,
    flags: Int? = null,
    data: (Intent) -> Any = {  }
) {
    runCatching {
        this.startActivity(Intent(this, dest).apply {
            if (flags != null) {
                this.flags = flags
            }
            if (clearTask) {
                this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            data.invoke(this)

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

fun Context.sendEmail(
    address: String,
    subject: String,
    body: String,
    onError: (msg: String) -> Unit
) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:") // only email apps should handle this
        putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        onError.invoke("No Apps found to complete Action")
    }
}

/**
 *  Caution when using this function
 * Note: This method requires the following permissions to be declared in the AndroidManifest.xml file:
 * - ACCESS_WIFI_STATE
 * - CHANGE_WIFI_STATE
 * - ACCESS_NETWORK_STATE
 * - INTERNET
 * - CHANGE_NETWORK_STATE
 *
 * Additionally, these permissions must be requested at runtime for devices running Android 6.0 (API level 23) or higher.
 *
 * @throws SecurityException if the necessary permissions are not granted.
 * */

@Throws(SecurityException::class)
fun Context.connectToWifi(ssid: String, pwd: String) {
    val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val wifiConfig = WifiConfiguration().apply {
        SSID = String.format("\"%s\"", ssid)
        preSharedKey = String.format("\"%s\"", pwd)
    }

    val netId = wifiManager.addNetwork(wifiConfig)
    wifiManager.disconnect()
    wifiManager.enableNetwork(netId, true)
    wifiManager.reconnect()
}


fun Context.openMoreApps(devName:String){
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://search?q=pub:$devName")
            )
        )
    } catch (anfe: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/developer?id=$devName")
            )
        )
    }
}

