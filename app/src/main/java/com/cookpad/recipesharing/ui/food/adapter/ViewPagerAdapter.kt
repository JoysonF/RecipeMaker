package com.cookpad.recipesharing.ui.food.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.databinding.ItemPreviewImageBinding
import com.cookpad.recipesharing.util.ext.loadImage

class ViewPagerAdapter constructor(
    private val recipeImages: List<String>
) : RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemPreviewImageBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPreviewImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return recipeImages.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            binding.recipeImage.loadImage(
                url = recipeImages[position],
                placeholderImage = R.drawable.ic_food_placeholder,
                error = R.drawable.ic_error_image
            )
        }
    }
}
