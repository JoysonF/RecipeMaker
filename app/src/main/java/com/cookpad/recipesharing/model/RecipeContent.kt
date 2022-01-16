package com.cookpad.recipesharing.model

import com.google.gson.annotations.SerializedName

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
)
