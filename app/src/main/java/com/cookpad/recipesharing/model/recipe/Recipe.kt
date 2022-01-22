package com.cookpad.recipesharing.model.recipe

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Recipe(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("ingredients")
    val ingredients: List<String>,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("steps")
    val steps: List<Step>,
    @SerializedName("story")
    val story: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("user")
    val user: User
)

@Parcelize
data class Step(
    @SerializedName("description")
    val description: String,
    @SerializedName("image_urls")
    val imageUrls: List<String>
) : Parcelable

data class User(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String
)