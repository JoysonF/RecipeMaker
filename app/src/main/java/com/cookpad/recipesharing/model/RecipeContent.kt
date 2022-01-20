package com.cookpad.recipesharing.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeContent(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("preview_image_urls")
    val previewImageUrls: List<String>,
    @SerializedName("recipe_count")
    val recipe_count: Int,
    @SerializedName("title")
    val title: String
) : Parcelable
