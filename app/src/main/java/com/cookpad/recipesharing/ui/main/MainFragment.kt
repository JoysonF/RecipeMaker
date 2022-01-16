package com.cookpad.recipesharing.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.data.Result
import com.cookpad.recipesharing.databinding.MainFragmentBinding
import com.cookpad.recipesharing.model.RecipeContent
import com.cookpad.recipesharing.ui.main.adapter.RecipeCollectionAdapter
import com.cookpad.recipesharing.util.ext.hide
import com.cookpad.recipesharing.util.ext.show
import com.cookpad.recipesharing.util.ext.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val recipeAdapter by lazy {
        RecipeCollectionAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        initViews()
        observeRecipe()
        observeActionEvent()
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

    private fun observeActionEvent() {
        mainViewModel.getErrorMsg().observe(this) { msgId ->
            binding.root.showSnackBar(getString(msgId)) {
                mainViewModel.getRecipeCollection()
            }
        }
    }

    private fun observeRecipe() {
        mainViewModel.recipeCollection.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Result.Error -> {
                    showLoading(false)
                }
                Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    if (response.data.isNotEmpty()) {
                        setAdapter(response.data)
                        showLoading(false)
                    } else {
                        binding.errorMsg.show()
                        binding.errorMsg.text = getString(R.string.msg_no_recipes)
                    }
                }
            }
        }
    }

    private fun setAdapter(data: List<RecipeContent>) {
        binding.errorMsg.hide()
        recipeAdapter.submitList(data)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pullDownRefreshLayout.isRefreshing = isLoading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}