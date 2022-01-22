package com.cookpad.recipesharing.ui.food.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.data.Result
import com.cookpad.recipesharing.data.source.repository.recipe.RecipeCollectionRepository
import com.cookpad.recipesharing.di.IoDispatcher
import com.cookpad.recipesharing.model.recipe.Recipe
import com.cookpad.recipesharing.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val repository: RecipeCollectionRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _recipeCollection = MutableLiveData<Result<List<Recipe>>>()
    val recipeCollection: LiveData<Result<List<Recipe>>>
        get() = _recipeCollection

    /**
     * Used for one time events to notify the views ..like network errors / api errors
     */
    private val actionEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    fun getErrorMsg(): SingleLiveEvent<Int> {
        return actionEvent
    }

    fun getAllRecipes(collectionId: Int) {
        runCatching {
            _recipeCollection.postValue(Result.Loading)
            viewModelScope.launch(dispatcher) {
                val response = repository.getAllRecipes(collectionId.toString())
                _recipeCollection.postValue(Result.Success(response))
            }
        }.onFailure {
            _recipeCollection.postValue(it.message?.let { msg ->
                Result.Error(msg)
            })
            actionEvent.postValue(handleError(it))
        }
    }

    /**
     * Handle exceptions
     */
    private fun handleError(e: Throwable): Int = when (e) {
        is IOException -> R.string.error_no_connection
        else -> R.string.error_server
    }
}
