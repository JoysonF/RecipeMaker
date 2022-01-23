package com.cookpad.recipesharing.util.ext

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadImage(
    url: String,
    @DrawableRes placeholderImage: Int,
    @DrawableRes error: Int
) {
    Glide.with(context)
        .load(url)
        .placeholder(ColorDrawable(Color.GRAY))
        .error(error)
        .into(this)
}

fun ImageView.loadCircularImage(
    url: String,
    @DrawableRes placeholderImage: Int,
    @DrawableRes error: Int
) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .placeholder(placeholderImage)
        .error(error)
        .into(this)
}
