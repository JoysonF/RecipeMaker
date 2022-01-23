package com.cookpad.recipesharing.ui.recipe.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.databinding.ItemRecipeBinding
import com.cookpad.recipesharing.model.recipe.Recipe
import com.cookpad.recipesharing.util.ext.loadImage
import javax.inject.Inject

class RecipeListAdapter @Inject constructor(
    private val onRecipeItemClick: (recipe: Recipe) -> Unit
) : ListAdapter<Recipe, RecipeListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.apply {
                recipeTitle.text = recipe.title

                imgFoodCollection.loadImage(
                    recipe.imageUrl
                )

                binding.root.setOnClickListener {
                    onRecipeItemClick(recipe)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }
}
