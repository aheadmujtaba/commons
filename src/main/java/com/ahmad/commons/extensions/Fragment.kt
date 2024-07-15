package com.ahmad.commons.extensions

import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment

fun Fragment.startActivity(dest: Class<*>) {
    requireContext().startActivity(dest)
}

fun <T> Fragment.askPermission(permissionLauncher: ActivityResultLauncher<T>, permission: T) {
    requireContext().askPermission(permissionLauncher, permission)
}

fun Fragment.copyTextToClipboard(label: String = "", text: String) {
    requireContext().copyTextToClipboard(label, text)
}

fun Fragment.shareTextVia(text: String) {
    requireContext().shareTextVia(text)
}

fun Fragment.addToContacts(phone: String) {
    requireContext().addToContact(phone)
}

fun Fragment.shareEmailVia(email: String, subject: String, body: String) {
    requireContext().shareEmailVia(email, subject, body)
}

fun Fragment.openUrl(urlString: String) {
    requireActivity().openUrl(urlString)
}
fun Fragment.shareContact(vCard:String){
    requireContext().shareContactVCard(vCard)
}