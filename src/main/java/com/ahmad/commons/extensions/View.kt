package com.ahmad.commons.extensions

import android.view.View

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}
fun View.makeGone(b:Boolean) {
    if(b){
        this.makeGone()
    }else{
        this.makeVisible()
    }
}

fun Boolean.toVisibility() {
    if (this) View.VISIBLE
    else View.GONE
}

fun View.changeVisibilityWhen(condition: Boolean) {
    if (condition) this.makeVisible()
    else this.makeGone()
}
