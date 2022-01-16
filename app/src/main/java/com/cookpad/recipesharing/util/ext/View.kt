package com.cookpad.recipesharing.util.ext

import android.view.View

/**
 * Extension functions to manage the visibility properties of a view
 */
fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}
