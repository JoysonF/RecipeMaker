package com.cookpad.recipesharing.ui.food.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.data.Result
import com.cookpad.recipesharing.databinding.DetailFragmentBinding
import com.cookpad.recipesharing.model.food.FoodContent
import com.cookpad.recipesharing.model.recipe.Recipe
import com.cookpad.recipesharing.ui.food.adapter.ViewPagerAdapter
import com.cookpad.recipesharing.ui.recipe.list.adapter.RecipeListAdapter
import com.cookpad.recipesharing.util.view.text.TextViewUtils
import com.cookpad.recipesharing.util.ext.hide
import com.cookpad.recipesharing.util.ext.show
import com.cookpad.recipesharing.util.ext.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodDetailFragment : Fragment() {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<FoodDetailFragmentArgs>()

    private lateinit var recipeViewPagerAdapter: ViewPagerAdapter

    private val detailViewModel by viewModels<FoodDetailViewModel>()

    private val recipeAdapter by lazy {
        RecipeListAdapter { recipe: Recipe -> onRecipeItemClicked(recipe.id) }
    }

    private fun onRecipeItemClicked(recipeId: Int) {
        val action =
            FoodDetailFragmentDirections.actionDetailFragmentToRecipeDetailsFragment(
                recipeId
            )
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        binding.shimmerViewContainer.visibility = View.VISIBLE
        setData(args.selectedRecipe)
        observeRecipe()
        observeErrorEvent()
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
                    showLoading(false)
                    if (response.data.isNotEmpty()) {
                        binding.noData.hide()
                        setRecipeAdapter(response.data)
                    } else {
                        binding.noData.show()
                        binding.noData.text = getString(R.string.msg_no_recipes)
                    }
                }
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

    private fun setData(selectedFood: FoodContent) {
        binding.apply {
            tvTitle.text = selectedFood.title
            TextViewUtils.addReadMore(
                selectedFood.description,
                binding.tvDescription,
                requireContext()
            )
            tvRecipeCount.text = HtmlCompat.fromHtml(
                "<font color=" + ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                ) + ">" + resources.getString(R.string.label_total_recipe_count) + "</font>" + selectedFood.recipe_count,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
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

    private fun observeErrorEvent() {
        detailViewModel.getErrorMsg().observe(this) { msgId ->
            binding.root.showSnackBar(getString(msgId)) {
                detailViewModel.getAllRecipes(args.selectedRecipe.id)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

