package com.cookpad.recipesharing.ui.recipe.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.data.Result
import com.cookpad.recipesharing.databinding.RecipeDetailsFragmentBinding
import com.cookpad.recipesharing.model.recipe.Recipe
import com.cookpad.recipesharing.model.recipe.Step
import com.cookpad.recipesharing.ui.food.adapter.ViewPagerAdapter
import com.cookpad.recipesharing.util.DateUtils
import com.cookpad.recipesharing.util.ext.loadCircularImage
import com.cookpad.recipesharing.util.ext.loadImage
import com.cookpad.recipesharing.util.view.text.TextViewUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {

    private val recipeDetailsViewModel by viewModels<RecipeDetailsViewModel>()

    private var _binding: RecipeDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<RecipeDetailsFragmentArgs>()

    private lateinit var recipeViewPagerAdapter: ViewPagerAdapter

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipeDetailsFragmentBinding.inflate(inflater, container, false)
        observeRecipes()
        binding.shimmerViewContainer.visibility = View.VISIBLE
        recipeDetailsViewModel.getRecipe(args.recipeId.toString())
        return binding.root
    }

    private fun observeRecipes() {
        recipeDetailsViewModel.recipeCollection.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Error -> {
                    showLoading(false)
                }
                Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    setData(response.data)
                }
            }
        }
    }

    private fun setData(recipe: Recipe) {
        binding.apply {

            tvIngredients.visibility = View.VISIBLE
            tvSteps.visibility = View.VISIBLE
            wormDotsIndicator.visibility = View.VISIBLE

            recipeImage.loadImage(
                recipe.imageUrl
            )

            (resources.getString(R.string.label_published_at) + DateUtils.getCurrentDate(recipe.publishedAt)).also {
                tvPublishedDate.text = it
            }

            imgUserProfile.loadCircularImage(
                recipe.user.imageUrl
            )

            tvUserName.text = recipe.user.name
            TextViewUtils.addReadMore(recipe.story, tvDescription, requireContext())
            tvTitle.text = recipe.title
            addIngredient(recipe.ingredients)
            addSteps(recipe.steps)
        }
        setViewPagerAdapter(recipe.steps)
    }

    private fun setViewPagerAdapter(steps: List<Step>) {

        val previewImages = mutableListOf<String>()
        for (item in steps) {
            previewImages.addAll(item.imageUrls)
        }

        if (previewImages.isEmpty()) {
            binding.stepsPagerPreview.visibility = View.GONE
            binding.wormDotsIndicator.visibility = View.GONE
        } else {
            binding.stepsPagerPreview.visibility = View.VISIBLE
            binding.wormDotsIndicator.visibility = View.VISIBLE
        }
        binding.apply {
            recipeViewPagerAdapter = ViewPagerAdapter(previewImages)
            stepsPagerPreview.apply {
                adapter = recipeViewPagerAdapter
                binding.wormDotsIndicator.setViewPager2(stepsPagerPreview)
            }
        }
    }

    private fun addIngredient(ingredients: List<String>) {
        binding.containerIngredients.removeAllViews()
        for (ingredient in ingredients) {
            binding.apply {
                val ingredientItem: View =
                    layoutInflater.inflate(
                        R.layout.item_ingredients,
                        containerIngredients,
                        false
                    ) //Message view

                val ingredientTvName = ingredientItem.findViewById<TextView>(R.id.tvIngredientName)
                ingredientTvName.text = ingredient
                containerIngredients.addView(ingredientItem)
            }
        }
    }

    private fun addSteps(steps: List<Step>) {
        binding.containerSteps.removeAllViews()
        for (step in steps) {
            binding.apply {
                val stepsItem: View =
                    layoutInflater.inflate(
                        R.layout.item_steps,
                        containerSteps,
                        false
                    ) //Message view

                val tvStepName = stepsItem.findViewById<TextView>(R.id.tvStepName)
                tvStepName.text = step.description
                containerSteps.addView(stepsItem)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {

        if (isLoading) {
            binding.apply {
                shimmerViewContainer.startShimmerAnimation()
            }

        } else {
            binding.apply {
                shimmerViewContainer.stopShimmerAnimation()
                shimmerViewContainer.visibility = View.GONE
            }
        }
    }
}
