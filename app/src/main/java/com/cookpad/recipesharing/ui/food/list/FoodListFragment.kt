package com.cookpad.recipesharing.ui.food.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.data.Result
import com.cookpad.recipesharing.databinding.FoodListFragmentBinding
import com.cookpad.recipesharing.model.food.FoodContent
import com.cookpad.recipesharing.ui.food.adapter.FoodCollectionAdapter
import com.cookpad.recipesharing.util.ext.hide
import com.cookpad.recipesharing.util.ext.show
import com.cookpad.recipesharing.util.ext.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodListFragment : Fragment() {

    private val mainViewModel by viewModels<FoodListViewModel>()

    private var _binding: FoodListFragmentBinding? = null
    private val binding get() = _binding!!

    private val recipeAdapter by lazy {
        FoodCollectionAdapter { foodContent: FoodContent -> recipeItemClick(foodContent) }
    }

    private fun recipeItemClick(foodContent: FoodContent) {
        navigateToDetail(foodContent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FoodListFragmentBinding.inflate(inflater, container, false)
        initViews()
        observeRecipe()
        observeErrorEvent()
        mainViewModel.getRecipeCollection()
        return binding.root
    }

    private fun initViews() {
        binding.apply {
            recipeRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = recipeAdapter
            }

            pullDownRefreshLayout.setOnRefreshListener {
                mainViewModel.getRecipeCollection()
            }
        }
    }

    private fun observeErrorEvent() {
        mainViewModel.getErrorMsg().observe(this) { msgId ->
            binding.root.showSnackBar(getString(msgId)) {
                mainViewModel.getRecipeCollection()
            }
        }
    }

    private fun observeRecipe() {
        mainViewModel.foodCollection.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Error -> {
                    showLoading(false)
                }
                Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    if (response.data.isNotEmpty()) {
                        binding.noData.hide()
                        setAdapter(response.data)
                        showLoading(false)
                    } else {
                        showLoading(false)
                        binding.noData.show()
                        binding.noData.text = getString(R.string.msg_no_recipes)
                    }
                }
            }
        }
    }

    private fun setAdapter(data: List<FoodContent>) {
        binding.noData.hide()
        recipeAdapter.submitList(data)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pullDownRefreshLayout.isRefreshing = isLoading

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

    private fun navigateToDetail(foodContent: FoodContent) {
        val action =
            FoodListFragmentDirections.navigateToDetailFragment(
                foodContent
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
