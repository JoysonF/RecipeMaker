package com.cookpad.recipesharing.ui.recipe.details

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val repository: RecipeCollectionRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _recipeCollection = MutableLiveData<Result<Recipe>>()
    val recipeCollection: LiveData<Result<Recipe>>
        get() = _recipeCollection

    /**
     * Used for one time events to notify the views ..like network errors / api errors
     */
    private val actionEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    fun getErrorMsg(): SingleLiveEvent<Int> {
        return actionEvent
    }


    fun getRecipe(recipeId: String) {
        runCatching {
            _recipeCollection.postValue(Result.Loading)
            viewModelScope.launch(dispatcher) {
                delay(3000)
                val response = repository.getRecipe(recipeId)
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
