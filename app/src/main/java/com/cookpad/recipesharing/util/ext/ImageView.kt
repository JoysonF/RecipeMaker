package com.cookpad.recipesharing.util.ext

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
        .placeholder(placeholderImage)
        .error(error)
        .into(this)
}
