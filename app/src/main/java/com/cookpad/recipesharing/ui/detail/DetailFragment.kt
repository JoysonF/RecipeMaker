package com.cookpad.recipesharing.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.cookpad.recipesharing.databinding.DetailFragmentBinding
import com.cookpad.recipesharing.model.RecipeContent

class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    private lateinit var recipeAdapter: ViewPagerAdapter

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        setData(args.selectedRecipe)
        return binding.root
    }


    private fun setData(selectedRecipe: RecipeContent) {
        binding.apply {
            tvTitle.text = selectedRecipe.title
            tvDescription.text = selectedRecipe.description
            tvRecipeCount.text = "Total Recipe Count: " + selectedRecipe.recipe_count
            setAdapter(selectedRecipe)
        }
    }

    private fun setAdapter(selectedRecipe: RecipeContent) {
        binding.apply {
            recipeAdapter = ViewPagerAdapter(selectedRecipe.previewImageUrls)
            recipeViewPager.apply {
                adapter = recipeAdapter
                binding.wormDotsIndicator.setViewPager2(recipeViewPager)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
