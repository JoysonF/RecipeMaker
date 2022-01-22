package com.cookpad.recipesharing.ui.food.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.databinding.ItemFoodBinding
import com.cookpad.recipesharing.model.food.FoodContent
import com.cookpad.recipesharing.util.ext.loadImage

class FoodCollectionAdapter constructor(
    val itemClickListener: (FoodContent) -> Unit
) :
    ListAdapter<FoodContent, FoodCollectionAdapter.RecipeViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding =
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: FoodContent) {
            binding.apply {
                recipeTitle.text = model.title
                tvDescription.text = model.description

                val displayImage = getDisplayImage(model.previewImageUrls)

                imgFoodCollection.loadImage(
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

    class DiffCallback : DiffUtil.ItemCallback<FoodContent>() {
        override fun areItemsTheSame(oldItem: FoodContent, newItem: FoodContent) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FoodContent, newItem: FoodContent) =
            oldItem == newItem
    }
}
