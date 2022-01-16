package com.cookpad.recipesharing.util.ext

import android.view.View
import com.cookpad.recipesharing.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    message: String,
    action: () -> Unit
) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    snackBar.setAction(this.context.getString(R.string.action_retry)) {
        action.invoke()
    }
    snackBar.show()
}
