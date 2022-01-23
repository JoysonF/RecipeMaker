package com.cookpad.recipesharing.util.ext

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadImage(
    url: String
) {
    Glide.with(context)
        .load(url)
        .centerInside()
        .placeholder(ColorDrawable(Color.GRAY))
        .error(ColorDrawable(Color.GRAY))
        .into(this)
}

fun ImageView.loadCircularImage(
    url: String
) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .placeholder(ColorDrawable(Color.GRAY))
        .error(ColorDrawable(Color.GRAY))
        .into(this)
}
