package com.cookpad.recipesharing.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.databinding.ItemRecipeBinding
import com.cookpad.recipesharing.model.RecipeContent
import com.cookpad.recipesharing.util.ext.loadImage

class RecipeCollectionAdapter constructor(
    val itemClickListener: (RecipeContent) -> Unit
) :
    ListAdapter<RecipeContent, RecipeCollectionAdapter.RecipeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding =
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RecipeContent) {
            binding.apply {
                recipeTitle.text = model.description

                val displayImage = getDisplayImage(model.previewImageUrls)

                imageView.loadImage(
                    url = displayImage,
                    placeholderImage = R.drawable.ic_food_placeholder,
                    error = R.drawable.ic_error_image
                )

                root.setOnClickListener {
                    itemClickListener(model)
                }
            }
        }

        private fun getDisplayImage(previewImageUrls: List<String>?): String {
            return if (previewImageUrls.isNullOrEmpty()) {
                //TODO pass dummy image
                ""
            } else {
                previewImageUrls[0]
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<RecipeContent>() {
        override fun areItemsTheSame(oldItem: RecipeContent, newItem: RecipeContent) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipeContent, newItem: RecipeContent) =
            oldItem == newItem
    }
}
