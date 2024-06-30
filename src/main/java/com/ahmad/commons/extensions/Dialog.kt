package com.ahmad.commons.extensions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup.LayoutParams

fun Dialog.showWithTransparentBackground() {
    if (this.isShowing) {
        this.dismiss()
    }
    setCancelable(false)
    this.show()
    this.window?.setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
    this.window?.setBackgroundDrawable(
        ColorDrawable(
            Color.TRANSPARENT
        )
    )
}

fun Dialog.tryDismiss(){
    if(this.isShowing){
        this.dismiss()
    }
}