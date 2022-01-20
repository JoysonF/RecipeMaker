package com.cookpad.recipesharing.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.data.Result
import com.cookpad.recipesharing.databinding.DetailFragmentBinding
import com.cookpad.recipesharing.model.food.FoodContent
import com.cookpad.recipesharing.model.recipe.Recipe
import com.cookpad.recipesharing.util.ext.hide
import com.cookpad.recipesharing.util.ext.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    private lateinit var recipeViewPagerAdapter: ViewPagerAdapter

    private val detailViewModel by viewModels<DetailViewModel>()

    private val recipeAdapter by lazy {
        RecipeListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        setData(args.selectedRecipe)
        observeRecipe()
        detailViewModel.getAllRecipes(args.selectedRecipe.id)
        return binding.root
    }

    private fun observeRecipe() {
        detailViewModel.recipeCollection.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Error -> {
                    showLoading(false)
                }
                Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    if (response.data.isNotEmpty()) {
                        setRecipeAdapter(response.data)
                        showLoading(false)
                    } else {
                        binding.errorMsg.show()
                        binding.errorMsg.text = getString(R.string.msg_no_recipes)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressLoader.show()
        } else {
            binding.progressLoader.hide()
        }
    }


    private fun setData(selectedFood: FoodContent) {
        binding.apply {
            tvTitle.text = selectedFood.title
            tvDescription.text = selectedFood.description
            tvRecipeCount.text = "Total Recipe Count: " + selectedFood.recipe_count
            setViewPagerAdapter(selectedFood)
            setRecyclerViewAdapter()
        }
    }

    private fun setRecyclerViewAdapter() {
        binding.apply {
            recipeList.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = recipeAdapter
            }
        }

    }

    private fun setViewPagerAdapter(selectedFood: FoodContent) {
        binding.apply {
            recipeViewPagerAdapter = ViewPagerAdapter(selectedFood.previewImageUrls)
            recipeViewPager.apply {
                adapter = recipeViewPagerAdapter
                binding.wormDotsIndicator.setViewPager2(recipeViewPager)
            }
        }
    }

    private fun setRecipeAdapter(data: List<Recipe>) {
        binding.errorNoRecipe.hide()
        recipeAdapter.submitList(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
